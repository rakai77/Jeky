package com.example.jeky.core.data.interactor

import com.example.jeky.core.data.source.Resource
import com.example.jeky.core.data.source.remote.dto.response.PlacesResponse
import com.example.jeky.core.domain.repository.PlacesRepository
import com.example.jeky.core.domain.usecase.PlacesUseCase
import kotlinx.coroutines.flow.Flow

class PlacesInteractor constructor(private val placesRepository: PlacesRepository) : PlacesUseCase {
    override suspend fun getPlaces(keyword: String): Flow<Resource<PlacesResponse>> {
        return placesRepository.getPlaces(keyword)
    }
}