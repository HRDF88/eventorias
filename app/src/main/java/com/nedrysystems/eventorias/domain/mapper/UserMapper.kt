package com.nedrysystems.eventorias.domain.mapper

import com.google.firebase.auth.FirebaseUser
import com.nedrysystems.eventorias.domain.model.User

/**
 * Converts a Firebase [FirebaseUser] object into a [User] domain object.
 *
 * This function maps the essential fields from Firebase authentication to the
 * domain-level `User` model:
 * - `id`: The unique user identifier (`uid`).
 * - `name`: The display name of the user (if available), or an empty string if not.
 * - `email`: The user's email address (if available), or an empty string if not.
 * - `profilPicture`: The user's profile picture URL (if available), or an empty string if not.
 * - `asNotification`: A boolean indicating whether the user is enabled for notifications (default is `true`).
 *
 * @return the corresponding [User] domain object.
 */
fun FirebaseUser.toDomainUser(): User {
    return User(
        id = uid,
        name = displayName ?: "",
        email = email ?: "",
        profilPicture = photoUrl?.toString() ?: "",
        asNotification = true
    )
}

