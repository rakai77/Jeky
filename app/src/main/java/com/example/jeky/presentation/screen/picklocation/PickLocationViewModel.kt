package com.example.jeky.presentation.screen.picklocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jeky.JekyApplication
import com.example.jeky.core.data.source.Resource
import com.example.jeky.core.data.source.remote.dto.response.toDomain
import com.example.jeky.core.domain.usecase.PlacesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PickLocationViewModel constructor(private val placesUseCase: PlacesUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<PickLocationUiState>(PickLocationUiState.Idle)
    val uiState: StateFlow<PickLocationUiState> get() = _uiState.asStateFlow()

    private var getPlacesJob: Job? = null

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as JekyApplication)
                PickLocationViewModel(application.jekyContainer.placesUseCase)
            }
        }
    }

    fun getPlaces(keyword: String) {
        getPlacesJob?.cancel()
        getPlacesJob = viewModelScope.launch {
            _uiState.emit(PickLocationUiState.Loading)
            placesUseCase.getPlaces(keyword)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let {
                                _uiState.emit(PickLocationUiState.Success(it.toDomain()))
                            }
                        }
                        is Resource.Error -> {
                            _uiState.emit(PickLocationUiState.Error(result.message))
                        }
                        else -> Unit
                    }
                }
        }
    }
}