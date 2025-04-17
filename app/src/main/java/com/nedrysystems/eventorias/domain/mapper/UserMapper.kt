package com.nedrysystems.eventorias.domain.mapper

import com.google.firebase.auth.FirebaseUser
import com.nedrysystems.eventorias.domain.model.User
import com.nedrysystems.eventorias.ui.uiModel.UserUiModel
import com.nedrysystems.eventorias.utils.Base64Converter
import com.nedrysystems.eventorias.utils.BitmapConverter

fun FirebaseUser.toDomainUser(): User {
    return User(
        id = uid,
        name = displayName ?: "",
        email = email ?: "",
        profilPicture = photoUrl?.toString() ?: "",
        asNotification = true
    )
}

fun User.toFirestoreMap(): Map<String, Any> {
    return mapOf(
        "id" to id,
        "name" to name,
        "email" to email,
        "profilPicture" to profilPicture,
        "asNotification" to asNotification
    )
}

    // Extension pour mapper User → UserUiModel
    fun User.toUiModel(): UserUiModel {
        // Convertit la chaîne Base64 en tableau d’octets, puis en Bitmap
        val profileBitmap = BitmapConverter
            .fromByteArray(Base64Converter.fromBase64(profilPicture))

        return UserUiModel(
            id = id,
            name = name,
            email = email,
            profileImage = profileBitmap,
            hasNotification = asNotification
        )
    }
