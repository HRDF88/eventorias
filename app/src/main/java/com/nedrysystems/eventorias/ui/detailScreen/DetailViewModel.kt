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

/**
 * ViewModel responsible for managing the UI state of the event detail screen.
 *
 * It loads the event data using the provided event ID from the navigation arguments
 * and exposes it as a [StateFlow] of [DetailUiState]. It also handles retries and error resets.
 *
 * @param eventUseCases Use cases for retrieving events from the repository.
 * @param savedStateHandle Handle to access saved state and navigation arguments, notably the "eventId".
 *
 * @constructor Initializes the ViewModel and loads the event using the event ID.
 */
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

    /**
     * Retries loading the event using the stored [eventId].
     */
    fun retry() {
        loadEvent(eventId)
    }

    /**
     * Resets any displayed error message.
     */
    fun resetMessage() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    /**
     * Loads the event by its ID and updates the UI state accordingly.
     *
     * @param id The ID of the event to load.
     */
    private fun loadEvent(id: String) {
        viewModelScope.launch {
            eventUseCases.getEventById(id)
                .onStart {
                    _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                }
                .onEach { fetchedEvent ->
                    _uiState.value =
                        DetailUiState(event = fetchedEvent.toUiModel(), isLoading = false)
                }
                .catch {
                    _uiState.value = DetailUiState(error = R.string.error_load_event)
                }
                .launchIn(this)
        }
    }
}
