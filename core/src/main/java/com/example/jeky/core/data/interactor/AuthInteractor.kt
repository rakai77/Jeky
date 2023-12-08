package com.example.jeky.core.data.interactor

import com.example.jeky.core.data.source.Resource
import com.example.jeky.core.domain.model.User
import com.example.jeky.core.domain.repository.AuthRepository
import com.example.jeky.core.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepository: AuthRepository
) : AuthUseCase {
    override suspend fun login(email: String, password: String): Flow<Resource<User>> {
        return authRepository.login(email, password)
    }

    override suspend fun register(user: User): Flow<Resource<User>> {
        return authRepository.register(user)
    }
}