package com.nedrysystems.eventorias.ui.eventListScreen

import com.nedrysystems.eventorias.ui.uiModel.EventUiModel

data class EventListUiState(
    val isLoading: Boolean = false,
    val event: List<EventUiModel> = emptyList(),
    val error: Int? = null
)