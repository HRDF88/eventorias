package com.nedrysystems.eventorias.ui.eventListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.domain.mapper.toUiModel
import com.nedrysystems.eventorias.domain.useCase.event.container.EventUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventUseCases: EventUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventListUiState(isLoading = true))
    val uiState: StateFlow<EventListUiState> = _uiState

    init {
        loadAllEvents()
    }

    private fun loadAllEvents() {
        viewModelScope.launch {
            _uiState.value = EventListUiState(isLoading = true)

            try {
                eventUseCases.getAllEvents("").collect { events ->
                    val uiModels = events.map { it.toUiModel() }
                    _uiState.value = EventListUiState(
                        isLoading = false,
                        event = uiModels
                    )
                }
            } catch (e: Exception) {
                _uiState.value = EventListUiState(
                    error = R.string.error_load_user,
                    isLoading = false
                )
            }
        }
    }
}
