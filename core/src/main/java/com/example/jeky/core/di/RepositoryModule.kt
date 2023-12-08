package com.example.jeky.core.di

import com.example.jeky.core.data.repository.AuthRepositoryImpl
import com.example.jeky.core.data.repository.PlacesRepositoryImpl
import com.example.jeky.core.data.repository.UserRepositoryImpl
import com.example.jeky.core.domain.repository.AuthRepository
import com.example.jeky.core.domain.repository.PlacesRepository
import com.example.jeky.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(authRepository: AuthRepositoryImpl) : AuthRepository

    @Binds
    abstract fun providePlacesRepository(placesRepository: PlacesRepositoryImpl) : PlacesRepository

    @Binds
    abstract fun provideUserRepository(userRepository: UserRepositoryImpl) : UserRepository
}