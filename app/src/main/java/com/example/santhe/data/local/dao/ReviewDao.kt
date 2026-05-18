package com.example.santhe.data.local.dao

import androidx.room.*
import com.example.santhe.data.local.entity.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: Review)

    @Query("SELECT * FROM reviews WHERE stallId = :stallId ORDER BY timestamp DESC")
    fun getReviewsForStall(stallId: Int): Flow<List<Review>>

    @Query("SELECT * FROM reviews ORDER BY timestamp DESC")
    fun getAllReviews(): Flow<List<Review>>
}
