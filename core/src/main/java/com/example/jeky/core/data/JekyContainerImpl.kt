package com.example.jeky.core.data

import android.content.Context
import com.example.jeky.core.data.interactor.AuthInteractor
import com.example.jeky.core.data.interactor.PlacesInteractor
import com.example.jeky.core.data.interactor.UserInteractor
import com.example.jeky.core.data.repository.AuthRepositoryImpl
import com.example.jeky.core.data.repository.PlacesRepositoryImpl
import com.example.jeky.core.data.repository.UserRepositoryImpl
import com.example.jeky.core.data.source.local.datastore.JekyDataStore
import com.example.jeky.core.data.source.local.room.JekyDatabase
import com.example.jeky.core.data.source.remote.network.JekyService
import com.example.jeky.core.domain.JekyContainer
import com.example.jeky.core.domain.repository.AuthRepository
import com.example.jeky.core.domain.repository.PlacesRepository
import com.example.jeky.core.domain.repository.UserRepository
import com.example.jeky.core.domain.usecase.AuthUseCase
import com.example.jeky.core.domain.usecase.PlacesUseCase
import com.example.jeky.core.domain.usecase.UserUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JekyContainerImpl constructor(private val context: Context) : JekyContainer {

    private val jekyDatabase: JekyDatabase by lazy {
        JekyDatabase.getInstance(context)
    }

    private val jekyDataStore: JekyDataStore by lazy {
        JekyDataStore(context)
    }

    private fun retrofitClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    private val retrofitPlaces: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://places.googleapis.com")
        .client(retrofitClient())
        .build()

    private val jekyApiService: JekyService by lazy {
        retrofitPlaces.create(JekyService::class.java)
    }

    override val authRepository: AuthRepository
        get() = AuthRepositoryImpl(jekyDatabase.userDao())


    override val userRepository: UserRepository
        get() = UserRepositoryImpl(jekyDataStore)

    override val placesRepository: PlacesRepository
        get() = PlacesRepositoryImpl(jekyApiService)

    override val authUseCase: AuthUseCase
        get() = AuthInteractor(authRepository)

    override val userUseCase: UserUseCase
        get() = UserInteractor(userRepository)

    override val placesUseCase: PlacesUseCase
        get() = PlacesInteractor(placesRepository)
}