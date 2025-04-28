package com.nedrysystems.eventorias.ui.detailScreen

import com.nedrysystems.eventorias.ui.uiModel.EventUiModel

/**
 * UI state for the detail screen.
 */
data class DetailUiState(
    val event: EventUiModel? = null,
    val isLoading: Boolean = false,
    val error: Int? = null
)