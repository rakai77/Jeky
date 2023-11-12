package com.example.jeky.core.domain

import com.example.jeky.core.domain.repository.AuthRepository
import com.example.jeky.core.domain.repository.UserRepository
import com.example.jeky.core.domain.usecase.AuthUseCase
import com.example.jeky.core.domain.usecase.UserUseCase

interface JekyContainer {

    val authRepository: AuthRepository
    val userRepository: UserRepository
    val authUseCase: AuthUseCase
    val userUseCase: UserUseCase
}