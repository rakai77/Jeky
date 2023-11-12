package com.example.jeky.core.domain.usecase

import com.example.jeky.core.data.source.Resource
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    suspend fun isUserLogged() : Flow<Resource<Boolean>>
    suspend fun storeEmail(email: String)
    suspend fun logout()

}