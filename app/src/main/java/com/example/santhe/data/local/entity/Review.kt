package com.example.santhe.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val stallId: Int,
    val userId: Int,
    val rating: Int,
    val comment: String,
    val audioUri: String? = null,
    val photoUri: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
