package com.nedrysystems.eventorias.ui.auth

import com.nedrysystems.eventorias.domain.model.User

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorResId: Int? = null
)