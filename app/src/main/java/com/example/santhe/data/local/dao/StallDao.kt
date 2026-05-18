package com.example.santhe.data.local.dao

import androidx.room.*
import com.example.santhe.data.local.entity.Stall
import kotlinx.coroutines.flow.Flow

@Dao
interface StallDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStall(stall: Stall)

    @Query("SELECT * FROM stalls")
    fun getAllStalls(): Flow<List<Stall>>

    @Query("SELECT * FROM stalls WHERE category = :category")
    fun getStallsByCategory(category: String): Flow<List<Stall>>

    @Query("SELECT * FROM stalls WHERE dayOfWeek = :day")
    fun getStallsByDay(day: String): Flow<List<Stall>>

    @Query("SELECT * FROM stalls WHERE id = :id")
    suspend fun getStallById(id: Int): Stall?

    @Query("DELETE FROM stalls")
    suspend fun deleteAllStalls()
}
