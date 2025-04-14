package com.nedrysystems.eventorias.domain.model

import java.io.Serializable

data class User(
    val id: String,
    val name: String,
    val email: String,
    val profilPicture: String,
    val asNotification: Boolean
) : Serializable {
    constructor() : this("", "", "", "", true)
}
