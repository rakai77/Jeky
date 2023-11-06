package com.example.jeky.core.data.repository

import com.example.jeky.core.data.source.local.room.dao.UserDao
import com.example.jeky.core.data.source.local.room.entity.toDomain
import com.example.jeky.core.domain.model.User
import com.example.jeky.core.domain.model.toEntity
import com.example.jeky.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl constructor(private val userDao: UserDao) : AuthRepository {
    override suspend fun login(email: String, password: String): Flow<List<User>> {
        return flow {
            emitAll(
                userDao.getUserByEmailAndPassword(email, password).map {
                    it.map { user ->
                        user.toDomain()
                    }
                }
            )
        }
    }

    override suspend fun register(user: User): Flow<User> {
        return flow {
            userDao.insertUser(user.toEntity())
            emit(user)
        }
    }
}