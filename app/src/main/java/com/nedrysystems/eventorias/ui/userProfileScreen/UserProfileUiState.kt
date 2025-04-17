package com.nedrysystems.eventorias.ui.userProfileScreen

import com.nedrysystems.eventorias.domain.model.User
import com.nedrysystems.eventorias.ui.uiModel.UserUiModel

data class UserProfileUiState(
    val isLoading: Boolean = false,
    val user: UserUiModel? = null,
    val error: Int? = null
)