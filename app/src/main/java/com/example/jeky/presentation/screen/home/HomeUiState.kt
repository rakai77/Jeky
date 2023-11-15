package com.example.jeky.presentation.screen.home

import RoutesResponse

sealed class HomeUiState {

    class Success(val data: RoutesResponse): HomeUiState()

    class Error(val message: String): HomeUiState()

    object Idle: HomeUiState()

    object Loading: HomeUiState()
}