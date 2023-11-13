package com.example.jeky.core.domain

import com.example.jeky.core.domain.repository.AuthRepository
import com.example.jeky.core.domain.repository.PlacesRepository
import com.example.jeky.core.domain.repository.UserRepository
import com.example.jeky.core.domain.usecase.AuthUseCase
import com.example.jeky.core.domain.usecase.PlacesUseCase
import com.example.jeky.core.domain.usecase.UserUseCase

interface JekyContainer {

    val authRepository: AuthRepository
    val userRepository: UserRepository
    val placesRepository: PlacesRepository
    val authUseCase: AuthUseCase
    val userUseCase: UserUseCase
    val placesUseCase: PlacesUseCase
}