package com.example.jeky.core.data

import android.content.Context
import com.example.jeky.core.data.repository.AuthRepositoryImpl
import com.example.jeky.core.data.source.local.room.JekyDatabase
import com.example.jeky.core.domain.JekyContainer
import com.example.jeky.core.domain.repository.AuthRepository

class JekyContainerImpl constructor(private val context: Context) : JekyContainer {

    private val jekyDatabase: JekyDatabase by lazy {
        JekyDatabase.getInstance(context)
    }

    override val authRepository: AuthRepository
        get() = AuthRepositoryImpl(jekyDatabase.userDao())

}