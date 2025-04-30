package com.nedrysystems.eventorias.domain.userUseCaseTest

import com.google.firebase.auth.FirebaseUser
import com.nedrysystems.eventorias.domain.mapper.toDomainUser
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*

class FirebaseUserExtensionTest {

    @Test
    fun `toDomainUser maps FirebaseUser to User correctly`() {
        val firebaseUser = mock(FirebaseUser::class.java)
        `when`(firebaseUser.uid).thenReturn("user123")
        `when`(firebaseUser.displayName).thenReturn("John Doe")
        `when`(firebaseUser.email).thenReturn("john.doe@example.com")
        val mockUri = mock<android.net.Uri>()
        `when`(mockUri.toString()).thenReturn("http://example.com/photo.jpg")
        `when`(firebaseUser.photoUrl).thenReturn(mockUri)

        val user = firebaseUser.toDomainUser()

        assertEquals("user123", user.id)
        assertEquals("John Doe", user.name)
        assertEquals("john.doe@example.com", user.email)
        assertEquals("http://example.com/photo.jpg", user.profilPicture)
        assertTrue(user.asNotification)
    }

    @Test
    fun `toDomainUser handles null fields gracefully`() {
        val firebaseUser = mock(FirebaseUser::class.java)
        `when`(firebaseUser.uid).thenReturn("user456")
        `when`(firebaseUser.displayName).thenReturn(null)
        `when`(firebaseUser.email).thenReturn(null)
        `when`(firebaseUser.photoUrl).thenReturn(null)

        val user = firebaseUser.toDomainUser()

        assertEquals("user456", user.id)
        assertEquals("", user.name)
        assertEquals("", user.email)
        assertEquals("", user.profilPicture)
        assertTrue(user.asNotification)
    }
}
