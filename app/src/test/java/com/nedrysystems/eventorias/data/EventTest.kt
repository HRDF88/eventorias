package com.nedrysystems.eventorias.data

import com.nedrysystems.eventorias.domain.model.Coordinate
import com.nedrysystems.eventorias.domain.model.Event
import org.junit.Assert.*
import org.junit.Test

class EventTest {

    @Test
    fun `should create Event with default values`() {

        val event = Event(
            id = "1234",
            tittle = "Concert",
            description = "A live concert event",
            timestamp = System.currentTimeMillis(),
            picture = "base64encodedstring",
            adresse = "123 Event Street",
            cordinateGps = Coordinate(48.8566, 2.3522), // (Paris)
            profilPicture = "profilebase64encodedstring"
        )

        // Vérification des propriétés
        assertEquals("1234", event.id)
        assertEquals("Concert", event.tittle)
        assertEquals("A live concert event", event.description)
        assertNotNull(event.timestamp)
        assertTrue(event.picture.isNotEmpty())
        assertEquals("123 Event Street", event.adresse)
        assertNotNull(event.cordinateGps)
        event.cordinateGps?.latitude?.let { assertEquals(48.8566, it, 0.0001) }
        event.cordinateGps?.longitude?.let { assertEquals(2.3522, it, 0.0001) }
        assertTrue(event.profilPicture.isNotEmpty())
    }

    @Test
    fun `should create Event with null Coordinate`() {

        val event = Event(
            id = "5678",
            tittle = "Webinar",
            description = "A virtual event",
            timestamp = System.currentTimeMillis(),
            picture = "base64encodedstring",
            adresse = "456 Webinar Avenue",
            cordinateGps = null,
            profilPicture = "profilebase64encodedstring"
        )

        assertNull(event.cordinateGps)
    }

    @Test
    fun `should check Event with empty picture and profilPicture`() {
        // Créez un Event avec des images vides
        val event = Event(
            id = "9012",
            tittle = "Hackathon",
            description = "A coding marathon event",
            timestamp = System.currentTimeMillis(),
            picture = "",
            adresse = "789 Coding Boulevard",
            cordinateGps = Coordinate(40.7128, -74.0060),  // New York City
            profilPicture = ""  // Profil picture vide
        )


        assertTrue(event.picture.isEmpty())
        assertTrue(event.profilPicture.isEmpty())
    }

    @Test
    fun `should verify Event timestamp is correct`() {

        val currentTimestamp = System.currentTimeMillis()
        val event = Event(
            id = "3456",
            tittle = "Meetup",
            description = "A meetup event",
            timestamp = currentTimestamp,
            picture = "base64encodedstring",
            adresse = "101 Meetup Lane",
            cordinateGps = Coordinate(34.0522, -118.2437),  // Los Angeles
            profilPicture = "profilebase64encodedstring"
        )


        assertEquals(currentTimestamp, event.timestamp)
    }

    @Test
    fun `should compare two Events with the same id and properties`() {

        val event1 = Event(
            id = "7890",
            tittle = "Coding Bootcamp",
            description = "An intensive coding bootcamp",
            timestamp = System.currentTimeMillis(),
            picture = "base64encodedstring",
            adresse = "102 Code Street",
            cordinateGps = Coordinate(37.7749, -122.4194),  // San Francisco
            profilPicture = "profilebase64encodedstring"
        )

        val event2 = Event(
            id = "7890",
            tittle = "Coding Bootcamp",
            description = "An intensive coding bootcamp",
            timestamp = event1.timestamp,
            picture = event1.picture,
            adresse = event1.adresse,
            cordinateGps = event1.cordinateGps,
            profilPicture = event1.profilPicture
        )


        assertEquals(event1, event2)
    }
}
