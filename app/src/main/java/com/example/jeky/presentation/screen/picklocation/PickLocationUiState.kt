package com.example.jeky.presentation.screen.picklocation

import com.example.jeky.core.domain.model.Places

sealed class PickLocationUiState {

    class Success(val data: Places): PickLocationUiState()

    class Error(val message: String): PickLocationUiState()

    object Idle: PickLocationUiState()

    object Loading: PickLocationUiState()
}