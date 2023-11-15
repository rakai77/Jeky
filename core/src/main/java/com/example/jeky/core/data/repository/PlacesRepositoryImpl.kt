package com.example.jeky.core.data.repository

import RoutesResponse
import com.example.jeky.core.data.source.Resource
import com.example.jeky.core.data.source.remote.SafeApiCall
import com.example.jeky.core.data.source.remote.dto.request.Destination
import com.example.jeky.core.data.source.remote.dto.request.LatLng
import com.example.jeky.core.data.source.remote.dto.request.Location
import com.example.jeky.core.data.source.remote.dto.request.Origin
import com.example.jeky.core.data.source.remote.dto.request.RoutesRequest
import com.example.jeky.core.data.source.remote.dto.response.PlacesResponse
import com.example.jeky.core.data.source.remote.network.JekyService
import com.example.jeky.core.domain.repository.PlacesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlacesRepositoryImpl constructor(private val jekyService: JekyService) : PlacesRepository,
    SafeApiCall {
    override suspend fun getPlaces(keyword: String): Flow<Resource<PlacesResponse>> {
        return flow {
            emit(safeApiCall { jekyService.getPlaces(keyword) })
        }
    }

    override suspend fun getPlacesRoute(
        origin: LatLng,
        destination: LatLng
    ): Flow<Resource<RoutesResponse>> {
        return flow {
            emit(
                safeApiCall {
                    jekyService.getPlaceRoutes(
                        "https://routes.googleapis.com/directions/v2:computeRoutes",
                        RoutesRequest(
                            Origin(Location(origin)),
                            Destination(Location(destination))
                        )
                    )
                }
            )
        }
    }
}