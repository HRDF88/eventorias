package com.nedrysystems.eventorias.ui.authScreen

import com.nedrysystems.eventorias.domain.model.User

/**
 * Data class representing the UI state for authentication.
 *
 * This class encapsulates all the necessary state information related to user authentication,
 * such as loading status, user data, sign-in status, and error reporting.
 *
 * @property isLoading Indicates whether an authentication-related operation is currently in progress.
 * @property user The currently authenticated [User], or `null` if no user is signed in.
 * @property errorResId Optional resource ID of an error message to be displayed, if any.
 * @property isSignedIn A boolean indicating whether the user is currently signed in.
 */
data class AuthUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorResId: Int? = null,
    val isSignedIn: Boolean = false
)