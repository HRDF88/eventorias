package com.nedrysystems.eventorias.domain.mapperTest


import com.google.common.truth.Truth.assertThat
import com.google.firebase.firestore.DocumentSnapshot
import com.nedrysystems.eventorias.domain.mapper.toEvent
import com.nedrysystems.eventorias.domain.mapper.toFirestoreMap
import com.nedrysystems.eventorias.domain.model.Coordinate
import com.nedrysystems.eventorias.domain.model.Event
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`


class EventMapperTest {

    @Test
    fun `toFirestoreMap returns correct map`() {
        val event = Event(
            id = "123",
            tittle = "My Event",
            description = "An awesome event",
            timestamp = 1714437123000L,
            picture = "base64string",
            adresse = "123 Main Street",
            cordinateGps = Coordinate(48.8566, 2.3522),
            profilPicture = "profileBase64"
        )

        val map = event.toFirestoreMap()

        assertThat(map["id"]).isEqualTo("123")
        assertThat(map["tittle"]).isEqualTo("My Event")
        assertThat(map["description"]).isEqualTo("An awesome event")
        assertThat(map["timestamp"]).isEqualTo(1714437123000L)
        assertThat(map["picture"]).isEqualTo("base64string")
        assertThat(map["adresse"]).isEqualTo("123 Main Street")
        assertThat(map["profilPicture"]).isEqualTo("profileBase64")

        val gps = map["cordinateGps"] as? Map<*, *>
        assertThat(gps).isNotNull()
        assertThat(gps!!["latitude"]).isEqualTo(48.8566)
        assertThat(gps["longitude"]).isEqualTo(2.3522)
    }

    @Test
    fun `toEvent returns correct Event from DocumentSnapshot`() {
        val snapshot = Mockito.mock(DocumentSnapshot::class.java)

        val fakeData = mapOf(
            "id" to "456",
            "tittle" to "Test Event",
            "description" to "Some description",
            "timestamp" to 1714437123000L,
            "picture" to "imgData",
            "adresse" to "456 Elm Street",
            "cordinateGps" to mapOf(
                "latitude" to 40.7128,
                "longitude" to -74.0060
            ),
            "profilPicture" to "profileImg"
        )

        `when`(snapshot.data).thenReturn(fakeData)

        val event = snapshot.toEvent()

        assertThat(event).isNotNull()
        assertThat(event!!.id).isEqualTo("456")
        assertThat(event.tittle).isEqualTo("Test Event")
        assertThat(event.description).isEqualTo("Some description")
        assertThat(event.timestamp).isEqualTo(1714437123000L)
        assertThat(event.picture).isEqualTo("imgData")
        assertThat(event.adresse).isEqualTo("456 Elm Street")
        assertThat(event.cordinateGps?.latitude).isEqualTo(40.7128)
        assertThat(event.cordinateGps?.longitude).isEqualTo(-74.0060)
        assertThat(event.profilPicture).isEqualTo("profileImg")
    }

    @Test
    fun `toEvent returns null if snapshot has no data`() {
        val snapshot = Mockito.mock(DocumentSnapshot::class.java)
        `when`(snapshot.data).thenReturn(null)

        val result = snapshot.toEvent()
        assertThat(result).isNull()
    }

    @Test
    fun `toFirestoreMap handles null GPS by setting zero coordinates`() {
        val event = Event(
            id = "789",
            tittle = "No GPS Event",
            description = "No coordinates",
            timestamp = 0,
            picture = "",
            adresse = "Nowhere",
            cordinateGps = null,
            profilPicture = ""
        )

        val map = event.toFirestoreMap()
        val gps = map["cordinateGps"] as Map<*, *>
        assertThat(gps["latitude"]).isEqualTo(0.0)
        assertThat(gps["longitude"]).isEqualTo(0.0)
    }
}
