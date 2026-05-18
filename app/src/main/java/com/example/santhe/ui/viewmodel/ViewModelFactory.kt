package com.example.santhe.ui.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.santhe.data.repository.AuthRepository
import com.example.santhe.data.repository.StallRepository

class ViewModelFactory(
    private val authRepository: AuthRepository? = null,
    private val stallRepository: StallRepository? = null,
    private val prefs: SharedPreferences? = null
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(authRepository!!, prefs!!) as T
            }
            modelClass.isAssignableFrom(StallViewModel::class.java) -> {
                StallViewModel(stallRepository!!) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
