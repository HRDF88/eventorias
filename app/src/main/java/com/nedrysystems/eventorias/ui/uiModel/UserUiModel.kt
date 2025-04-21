package com.nedrysystems.eventorias.ui.uiModel

import androidx.compose.ui.graphics.painter.Painter

// UI model pour l’écran Profil
data class UserUiModel(
    val id: String,
    val name: String,
    val email: String,
    val profileImage: Painter,
    val hasNotification: Boolean
)


