package com.nedrysystems.eventorias.ui.eventListScreen

import com.nedrysystems.eventorias.ui.uiModel.EventUiModel

/**
 * Represents the UI state for a screen displaying a list of events.
 *
 * This data class is used to manage the state in a reactive UI, such as with Jetpack Compose.
 *
 * @property isLoading Indicates whether the event list is currently being loaded.
 * @property events The full list of events retrieved from the data source.
 * @property filteredEvents The list of events after applying search or filters.
 * @property error Optional resource ID representing an error message to display.
 * @property isSearchVisible Controls whether the search bar is visible in the UI.
 * @property searchQuery The current text entered in the search bar.
 * @property sortDescending Indicates whether the event list is sorted in descending order.
 */
data class EventListUiState(
    val isLoading: Boolean = false,
    val events: List<EventUiModel> = emptyList(),
    val filteredEvents: List<EventUiModel> = emptyList(),
    val error: Int? = null,
    val isSearchVisible: Boolean = false,
    val searchQuery: String = "",
    val sortDescending: Boolean = true
)
