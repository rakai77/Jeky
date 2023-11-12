package com.example.jeky.core.data

import android.content.Context
import com.example.jeky.core.data.interactor.AuthInteractor
import com.example.jeky.core.data.interactor.UserInteractor
import com.example.jeky.core.data.repository.AuthRepositoryImpl
import com.example.jeky.core.data.repository.UserRepositoryImpl
import com.example.jeky.core.data.source.local.datastore.JekyDataStore
import com.example.jeky.core.data.source.local.room.JekyDatabase
import com.example.jeky.core.domain.JekyContainer
import com.example.jeky.core.domain.repository.AuthRepository
import com.example.jeky.core.domain.repository.UserRepository
import com.example.jeky.core.domain.usecase.AuthUseCase
import com.example.jeky.core.domain.usecase.UserUseCase

class JekyContainerImpl constructor(private val context: Context) : JekyContainer {

    private val jekyDatabase: JekyDatabase by lazy {
        JekyDatabase.getInstance(context)
    }

    private val jekyDataStore: JekyDataStore by lazy {
        JekyDataStore(context)
    }

    override val authRepository: AuthRepository
        get() = AuthRepositoryImpl(jekyDatabase.userDao())


    override val userRepository: UserRepository
        get() = UserRepositoryImpl(jekyDataStore)

    override val authUseCase: AuthUseCase
        get() = AuthInteractor(authRepository)

    override val userUseCase: UserUseCase
        get() = UserInteractor(userRepository)
}