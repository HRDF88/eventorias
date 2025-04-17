package com.nedrysystems.eventorias.ui.addScreen

import com.nedrysystems.eventorias.ui.uiModel.EventUiModel

data class AddUiState(
    val isLoading: Boolean = false,
    val error: Int? = null,
    val event : EventUiModel? = null
)
