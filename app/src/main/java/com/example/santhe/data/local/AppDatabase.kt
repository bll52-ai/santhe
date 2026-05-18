package com.example.santhe.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.santhe.data.local.dao.ReviewDao
import com.example.santhe.data.local.dao.StallDao
import com.example.santhe.data.local.dao.UserDao
import com.example.santhe.data.local.entity.Review
import com.example.santhe.data.local.entity.Stall
import com.example.santhe.data.local.entity.UserAccount

@Database(entities = [UserAccount::class, Stall::class, Review::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun stallDao(): StallDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "santhe_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
