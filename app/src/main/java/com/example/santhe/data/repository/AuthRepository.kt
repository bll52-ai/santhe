package com.example.santhe.data.repository

import com.example.santhe.data.local.dao.UserDao
import com.example.santhe.data.local.entity.UserAccount
import kotlinx.coroutines.flow.Flow

class AuthRepository(private val userDao: UserDao) {
    suspend fun register(user: UserAccount) = userDao.insertUser(user)
    suspend fun login(username: String): UserAccount? = userDao.getUserByUsername(username)
    fun getUserById(id: Int): Flow<UserAccount?> = userDao.getUserById(id)
}
