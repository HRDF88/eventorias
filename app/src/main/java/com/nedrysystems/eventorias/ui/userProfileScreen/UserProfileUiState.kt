package com.nedrysystems.eventorias.ui.userProfileScreen

import com.nedrysystems.eventorias.domain.model.User

data class UserProfileUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: Int? = null
)