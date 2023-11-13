package com.example.jeky.core.data.repository

import com.example.jeky.core.data.source.Resource
import com.example.jeky.core.data.source.remote.SafeApiCall
import com.example.jeky.core.data.source.remote.dto.response.PlacesResponse
import com.example.jeky.core.data.source.remote.network.JekyService
import com.example.jeky.core.domain.repository.PlacesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlacesRepositoryImpl constructor(private val jekyService: JekyService) : PlacesRepository, SafeApiCall {
    override suspend fun getPlaces(keyword: String): Flow<Resource<PlacesResponse>> {
        return flow {
            emit(safeApiCall { jekyService.getPlaces(keyword) })
        }
    }
}