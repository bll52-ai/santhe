package com.example.santhe.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stalls")
data class Stall(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val category: String, // Food, Market, Craft
    val latitude: Double,
    val longitude: Double,
    val dayOfWeek: String? = null, // For Santhes: Monday, Tuesday, etc.
    val specialtyTags: String, // Comma-separated tags
    val imageUrl: String? = null
)
