package com.example.jeky.presentation.screen.register

import com.example.jeky.core.domain.model.User

sealed class RegisterUiState {
    class Success(val data: User): RegisterUiState()

    class Error(val message: String): RegisterUiState()

    object Idle: RegisterUiState()

    object Loading: RegisterUiState()
}