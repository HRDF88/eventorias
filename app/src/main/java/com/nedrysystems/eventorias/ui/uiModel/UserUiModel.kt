package com.nedrysystems.eventorias.ui.uiModel

import android.graphics.Bitmap
import com.nedrysystems.eventorias.domain.model.User
import com.nedrysystems.eventorias.utils.Base64Converter
import com.nedrysystems.eventorias.utils.BitmapConverter

// UI model pour l’écran Profil
data class UserUiModel(
    val id: String,
    val name: String,
    val email: String,
    val profileImage: Bitmap,
    val hasNotification: Boolean
)


