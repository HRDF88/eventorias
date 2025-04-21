package com.nedrysystems.eventorias.ui.addScreen

import androidx.lifecycle.ViewModel
import com.nedrysystems.eventorias.domain.useCase.event.container.EventUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(private val eventUseCases: EventUseCases) : ViewModel(){

    private val _uiState = MutableStateFlow(AddUiState(isLoading = false))
    val uiState: StateFlow<AddUiState> = _uiState
}