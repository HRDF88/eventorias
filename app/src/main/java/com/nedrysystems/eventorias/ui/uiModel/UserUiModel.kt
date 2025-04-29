package com.nedrysystems.eventorias.ui.uiModel

import androidx.compose.ui.graphics.painter.Painter

/**
 * UI model used to display user information on the Profile screen.
 *
 * This model includes both the essential user data and display-ready elements (e.g., profile image).
 *
 * @property id Unique identifier of the user.
 * @property name Display name of the user.
 * @property email Email address of the user.
 * @property profileImage Profile image of the user, already converted into a [Painter] for Jetpack Compose UI.
 * @property hasNotification Indicates whether the user has enabled notifications.
 */
data class UserUiModel(
    val id: String,
    val name: String,
    val email: String,
    val profileImage: Painter,
    val hasNotification: Boolean
)


