package com.example.santhe.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.santhe.data.local.entity.Review
import com.example.santhe.data.local.entity.Stall
import com.example.santhe.data.repository.StallRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StallViewModel(private val repository: StallRepository) : ViewModel() {
    val allStalls = repository.allStalls
    val allReviews = repository.allReviews

    private val _selectedStall = MutableStateFlow<Stall?>(null)
    val selectedStall = _selectedStall.asStateFlow()

    fun selectStall(stall: Stall) {
        _selectedStall.value = stall
    }

    fun addStall(stall: Stall) {
        viewModelScope.launch {
            repository.addStall(stall)
        }
    }

    fun addReview(review: Review) {
        viewModelScope.launch {
            repository.addReview(review)
        }
    }

    fun getReviewsForStall(stallId: Int) = repository.getReviewsForStall(stallId)
}
