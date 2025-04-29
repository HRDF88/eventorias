package com.nedrysystems.eventorias.ui.eventListScreen

import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.domain.mapper.toUiModel
import com.nedrysystems.eventorias.domain.useCase.event.container.EventUseCases
import com.nedrysystems.eventorias.ui.uiModel.EventUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state of the event list screen.
 *
 * It handles loading, filtering, and sorting of events, and exposes a [StateFlow] of [EventListUiState]
 * for UI observation using Jetpack Compose or other reactive UI frameworks.
 *
 * @property eventUseCases A container of use cases related to events (e.g., fetching all events).
 */
@HiltViewModel
class EventListViewModel @Inject constructor(
    private val eventUseCases: EventUseCases
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _sortDescending = MutableStateFlow(true)
    val sortDescending: StateFlow<Boolean> = _sortDescending

    private val _events = MutableStateFlow<List<EventUiModel>>(emptyList())
    private val _isLoading = MutableStateFlow(true)
    private val _error = MutableStateFlow<Int?>(null)

    /**
     * Combined UI state including loading state, filtered and sorted events,
     * and possible errors.
     */
    val uiState: StateFlow<EventListUiState> = combine(
        _events, _searchQuery, _sortDescending, _isLoading, _error
    ) { events, query, descending, isLoading, error ->
        val filtered = if (query.isNotBlank()) {
            events.filter { it.title.contains(query, ignoreCase = true) }
        } else events

        val sorted = if (descending) {
            filtered.sortedByDescending { it.timestamp }
        } else {
            filtered.sortedBy { it.timestamp }
        }

        EventListUiState(
            isLoading = isLoading,
            error = error,
            events = sorted
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, EventListUiState(isLoading = true))

    /**
     * Updates the current search query for filtering the event list.
     */
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    /**
     * Toggles the sort order between ascending and descending based on the event timestamp.
     */
    fun toggleSortOrder() {
        _sortDescending.value = !_sortDescending.value
    }

    /**
     * Loads all events from the repository and updates the internal state.
     * In case of error, sets an error resource ID.
     */
    @OptIn(UnstableApi::class)
    fun loadAllEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                eventUseCases.getAllEvents("").collect { events ->
                    _events.value = events.map { it.toUiModel() }
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                Log.e("EventListViewModel", "Erreur: ${e.message}", e)
                _isLoading.value = false
                _error.value = R.string.error_load_user
            }
        }
    }
}


