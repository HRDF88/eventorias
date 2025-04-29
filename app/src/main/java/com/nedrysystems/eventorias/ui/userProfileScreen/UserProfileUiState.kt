package com.nedrysystems.eventorias.ui.userProfileScreen

import com.nedrysystems.eventorias.domain.model.User

/**
 * UI state representing the current status of the user profile screen.
 *
 * This state is used to manage the UI's response to user data loading, errors, and notification settings.
 *
 * @property isLoading Indicates whether the user profile data is currently being loaded.
 * @property user The currently loaded user data, or null if not available.
 * @property error Resource ID of an error message to be displayed, if any.
 * @property asNotification Indicates whether notifications are enabled for the user.
 */
data class UserProfileUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: Int? = null,
    val asNotification: Boolean = true
)