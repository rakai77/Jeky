package com.example.jeky.core.data.repository

import com.example.jeky.core.data.source.Resource
import com.example.jeky.core.data.source.local.room.dao.UserDao
import com.example.jeky.core.data.source.local.room.entity.toDomain
import com.example.jeky.core.domain.model.User
import com.example.jeky.core.domain.model.toEntity
import com.example.jeky.core.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepositoryImpl constructor(private val userDao: UserDao) : AuthRepository {
    override suspend fun login(email: String, password: String): Flow<Resource<User>> {
        return flow {
            val users = userDao.getUserByEmailAndPassword(email, password).first()
            if (users.isNotEmpty()) {
                emit(Resource.Success(users.first().toDomain()))
            } else {
                emit(Resource.Error(-1, "Ups, invalid login"))
            }
        }.catch { e ->
            emit(Resource.Error(-1, e.message ?: "Ups, something wrong"))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun register(user: User): Flow<Resource<User>> {
        return flow<Resource<User>> {
            userDao.insertUser(user.toEntity())
            emit(Resource.Success(user))
        }.catch { e ->
            emit(Resource.Error(-1, e.message ?: "Ups, something wrong"))
        }.flowOn(Dispatchers.IO)
    }
}