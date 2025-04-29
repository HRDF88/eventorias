package com.nedrysystems.eventorias.ui.detailScreen

import com.nedrysystems.eventorias.ui.uiModel.EventUiModel

/**
 * Represents the UI state for the detail screen showing a specific event.
 *
 * This data class holds the current event to display, the loading state,
 * and any potential error resource ID to be shown in the UI.
 *
 * @property event The event data mapped for UI display. Null if not yet loaded or in case of error.
 * @property isLoading Indicates whether the event is currently being fetched.
 * @property error An optional resource ID of an error message to display, or null if there's no error.
 */
data class DetailUiState(
    val event: EventUiModel? = null,
    val isLoading: Boolean = false,
    val error: Int? = null
)