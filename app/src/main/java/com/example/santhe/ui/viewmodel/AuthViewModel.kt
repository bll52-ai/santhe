package com.example.santhe.ui.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.santhe.data.local.entity.UserAccount
import com.example.santhe.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
    private val prefs: SharedPreferences
) : ViewModel() {
    private val _currentUser = MutableStateFlow<UserAccount?>(null)
    val currentUser: StateFlow<UserAccount?> = _currentUser

    private val _isSessionLoading = MutableStateFlow(true)
    val isSessionLoading: StateFlow<Boolean> = _isSessionLoading

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    init {
        // Auto-login: Check if a user was previously logged in
        val savedUsername = prefs.getString("logged_in_user", null)
        if (savedUsername != null) {
            viewModelScope.launch {
                val user = repository.login(savedUsername)
                if (user != null) {
                    _currentUser.value = user
                }
                _isSessionLoading.value = false
            }
        } else {
            _isSessionLoading.value = false
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val existing = repository.login(username)
            if (existing != null) {
                _authState.value = AuthState.Error("Username already exists")
            } else {
                val newUser = UserAccount(username = username, email = email, password = password)
                repository.register(newUser)
                // Auto-login after registration
                _currentUser.value = newUser
                prefs.edit().putString("logged_in_user", username).apply()
                _authState.value = AuthState.Success
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = repository.login(username)
            if (user != null && user.password == password) {
                _currentUser.value = user
                prefs.edit().putString("logged_in_user", username).apply()
                _authState.value = AuthState.Success
            } else {
                _authState.value = AuthState.Error("Invalid credentials")
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        prefs.edit().remove("logged_in_user").apply()
        _authState.value = AuthState.Idle
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
