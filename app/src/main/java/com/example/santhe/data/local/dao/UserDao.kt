package com.example.santhe.data.local.dao

import androidx.room.*
import com.example.santhe.data.local.entity.UserAccount
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserAccount)

    @Query("SELECT * FROM user_accounts WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserAccount?

    @Query("SELECT * FROM user_accounts WHERE id = :id")
    fun getUserById(id: Int): Flow<UserAccount?>
}
