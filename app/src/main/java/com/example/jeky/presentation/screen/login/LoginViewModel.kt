package com.example.jeky.presentation.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jeky.JekyApplication
import com.example.jeky.core.data.source.Resource
import com.example.jeky.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    private val authRepository: AuthRepository
) : ViewModel() {


    private val _loginUiState = MutableSharedFlow<LoginUiState>()
    val loginUiState get() =  _loginUiState.asSharedFlow()


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JekyApplication)
                LoginViewModel(application.jekyContainer.authRepository)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginUiState.emit(LoginUiState.Loading)
            authRepository.login(email, password)
                .collect {
                    when (it) {
                        is Resource.Success -> {
                            _loginUiState.emit(LoginUiState.Success(it.data))
                        }
                        is Resource.Error -> {
                            _loginUiState.emit(LoginUiState.Error(it.message))
                        }
                        else -> Unit
                    }
                }
        }
    }
}