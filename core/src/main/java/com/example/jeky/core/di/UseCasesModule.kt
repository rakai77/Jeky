package com.example.jeky.core.di

import com.example.jeky.core.data.interactor.AuthInteractor
import com.example.jeky.core.data.interactor.PlacesInteractor
import com.example.jeky.core.data.interactor.UserInteractor
import com.example.jeky.core.domain.usecase.AuthUseCase
import com.example.jeky.core.domain.usecase.PlacesUseCase
import com.example.jeky.core.domain.usecase.UserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {

    @Binds
    abstract fun provideAuthUseCase(authInteractor: AuthInteractor) : AuthUseCase

    @Binds
    abstract fun providePlacesUseCase(placesInteractor: PlacesInteractor) : PlacesUseCase

    @Binds
    abstract fun provideUserUseCase(userInteractor: UserInteractor) : UserUseCase
}