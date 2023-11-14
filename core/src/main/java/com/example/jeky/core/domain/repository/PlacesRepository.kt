package com.example.jeky.core.domain.repository

import com.example.jeky.core.data.source.Resource
import com.example.jeky.core.data.source.remote.dto.response.GetPlacesRoutesResponse
import com.example.jeky.core.data.source.remote.dto.response.PlacesResponse
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {

    suspend fun getPlaces(keyword: String) : Flow<Resource<PlacesResponse>>
    suspend fun getPlaceRoutes(origin: String, destination: String): Flow<Resource<GetPlacesRoutesResponse>>
}