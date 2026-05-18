package com.example.santhe.data.repository

import com.example.santhe.data.local.dao.ReviewDao
import com.example.santhe.data.local.dao.StallDao
import com.example.santhe.data.local.entity.Review
import com.example.santhe.data.local.entity.Stall
import kotlinx.coroutines.flow.Flow

class StallRepository(private val stallDao: StallDao, private val reviewDao: ReviewDao) {
    val allStalls: Flow<List<Stall>> = stallDao.getAllStalls()
    val allReviews: Flow<List<Review>> = reviewDao.getAllReviews()

    fun getStallsByCategory(category: String) = stallDao.getStallsByCategory(category)
    fun getStallsByDay(day: String) = stallDao.getStallsByDay(day)
    suspend fun getStallById(id: Int) = stallDao.getStallById(id)

    suspend fun addStall(stall: Stall) = stallDao.insertStall(stall)
    suspend fun clearStalls() = stallDao.deleteAllStalls()
    suspend fun addReview(review: Review) = reviewDao.insertReview(review)
    fun getReviewsForStall(stallId: Int) = reviewDao.getReviewsForStall(stallId)
}
