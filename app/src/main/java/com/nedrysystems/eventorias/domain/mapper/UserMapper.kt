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