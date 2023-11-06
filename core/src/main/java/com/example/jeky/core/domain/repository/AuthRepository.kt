package com.example.jeky.core.domain.repository

import com.example.jeky.core.data.source.local.room.entity.UserEntity
import com.example.jeky.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun login(email: String, password: String) : Flow<List<User>>

    suspend fun register(user: User) : Flow<User>
}