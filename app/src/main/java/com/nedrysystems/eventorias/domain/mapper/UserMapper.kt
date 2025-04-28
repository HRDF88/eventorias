package com.nedrysystems.eventorias.domain.mapper

import com.google.firebase.auth.FirebaseUser
import com.nedrysystems.eventorias.domain.model.User

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
/*
    // Extension pour mapper User → UserUiModel
    @Composable
    fun User.toUiModel(): UserUiModel {
        // Créer un `Painter` Coil pour l'image de profil
        val profileImage: Painter = rememberImagePainter(
            data = profilPicture, // l'URL de l'image
            builder = {
                crossfade(true) // Ajoute une animation de fondu lors du chargement de l'image
                error(R.drawable.ic_error) // Affiche une image d'erreur si l'image ne peut pas être chargée
            }
        )

        return UserUiModel(
            id = id,
            name = name,
            email = email,
            profileImage = profileImage, // Image gérée par Coil
            hasNotification = asNotification
        )
    }
*/