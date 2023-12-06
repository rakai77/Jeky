package com.example.jeky.core.data.interactor

import RoutesResponse
import com.example.jeky.core.data.source.Resource
import com.example.jeky.core.data.source.remote.dto.request.LatLng
import com.example.jeky.core.data.source.remote.dto.response.PlacesResponse
import com.example.jeky.core.domain.repository.PlacesRepository
import com.example.jeky.core.domain.usecase.PlacesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlacesInteractor @Inject constructor(private val placesRepository: PlacesRepository) : PlacesUseCase {
    override suspend fun getPlaces(keyword: String): Flow<Resource<PlacesResponse>> {
        return placesRepository.getPlaces(keyword)
    }

    override suspend fun getPlaceRoutes(origin: LatLng, destination: LatLng): Flow<Resource<RoutesResponse>> {
        return placesRepository.getPlacesRoute(origin, destination)
    }
}