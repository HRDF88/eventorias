package com.nedrysystems.eventorias.ui.eventListScreen

import com.nedrysystems.eventorias.ui.uiModel.EventUiModel

data class EventListUiState(
    val isLoading: Boolean = false,
    val events: List<EventUiModel> = emptyList(),
    val filteredEvents: List<EventUiModel> = emptyList(),
    val error: Int? = null,
    val isSearchVisible: Boolean = false,
    val searchQuery: String = "",
    val sortDescending: Boolean = true
)
