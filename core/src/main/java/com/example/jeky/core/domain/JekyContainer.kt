package com.example.jeky.core.domain

import com.example.jeky.core.domain.repository.AuthRepository

interface JekyContainer {

    val authRepository: AuthRepository
}