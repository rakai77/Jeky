package com.example.jeky.core.data.interactor

import com.example.jeky.core.data.source.Resource
import com.example.jeky.core.domain.repository.UserRepository
import com.example.jeky.core.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.Flow

class UserInteractor constructor(
    private val userRepository: UserRepository
) : UserUseCase {
    override suspend fun isUserLogged(): Flow<Resource<Boolean>> {
        return userRepository.isUserLoggedIn()
    }

    override suspend fun storeEmail(email: String) {
        return userRepository.storeEmail(email)
    }

    override suspend fun logout() {
        return userRepository.logout()
    }

}