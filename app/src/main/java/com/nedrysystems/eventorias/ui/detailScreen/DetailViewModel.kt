package com.nedrysystems.eventorias.ui.detailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.domain.mapper.toUiModel
import com.nedrysystems.eventorias.domain.useCase.event.container.EventUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val eventUseCases: EventUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState(isLoading = true))
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val eventId: String = (savedStateHandle.get<String>("eventId") ?: run {
        _uiState.value = DetailUiState(error = R.string.error_id)
        return@run
    }).toString()

    init {

        loadEvent(eventId)
    }

    fun retry() {
        loadEvent(eventId)
    }

    fun resetMessage() {
        _uiState.value = _uiState.value.copy(error = null)
    }

     private fun loadEvent(id: String) {
        viewModelScope.launch {
            eventUseCases.getEventById(id)
                .onStart {
                    _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                }
                .onEach { fetchedEvent ->
                    _uiState.value = DetailUiState(event = fetchedEvent.toUiModel(), isLoading = false)
                }
                .catch {
                    _uiState.value = DetailUiState(error = R.string.error_load_event)
                }
                .launchIn(this)
        }
    }
}
