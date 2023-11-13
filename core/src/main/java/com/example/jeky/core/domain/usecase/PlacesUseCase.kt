package com.example.jeky.core.domain.usecase

import com.example.jeky.core.data.source.Resource
import com.example.jeky.core.data.source.remote.dto.response.PlacesResponse
import kotlinx.coroutines.flow.Flow

interface PlacesUseCase {

    suspend fun getPlaces(keyword: String) : Flow<Resource<PlacesResponse>>
}