package com.example.jeky.presentation.screen.home

import com.example.jeky.core.data.source.remote.dto.response.GetPlacesRoutesResponse

sealed class HomeUiState {

    class Success(val data: GetPlacesRoutesResponse): HomeUiState()

    class Error(val message: String): HomeUiState()

    object Idle: HomeUiState()

    object Loading: HomeUiState()
}