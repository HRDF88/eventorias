package com.nedrysystems.eventorias.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.firestore.FirebaseFirestore
import com.nedrysystems.eventorias.data.webService.firebase.CollectionEventFirebaseAPI
import com.nedrysystems.eventorias.domain.model.Coordinate
import com.nedrysystems.eventorias.domain.model.Event
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectionEventFirebaseAPIInstrumentedTest {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var api: CollectionEventFirebaseAPI

    @Before
    fun setup() {

        firestore = FirebaseFirestore.getInstance()
        firestore.useEmulator("10.0.2.2", 8080) // 10.0.2.2 = localhost depuis un émulateur Android

        api = CollectionEventFirebaseAPI(firestore)
    }

    @Test
    fun addEvent_savesAndRetrievesEvent() = runBlocking {

        val event = Event(
            id = "test_id",
            tittle = "Test Title",
            description = "Test description",
            timestamp = 123456789,
            picture = "",
            adresse = "Test address",
            cordinateGps = Coordinate(1.1, 2.2),
            profilPicture = ""
        )


        api.add(event).first()

        val fetchedEvent = api.getEventById("test_id").first()

        assertEquals(event, fetchedEvent)
    }

    @Test
    fun getAllEvent_returnsFilteredEvents() =  runBlocking {
        // Créer plusieurs événements fictifs
        val event1 = Event(
            id = "event1_id",
            tittle = "Test Title",
            description = "Test description 1",
            timestamp = 123456789,
            picture = "",
            adresse = "Test address 1",
            cordinateGps = Coordinate(1.1, 2.2),
            profilPicture = ""
        )
        val event2 = Event(
            id = "event2_id",
            tittle = "Test Title",
            description = "Test description 2",
            timestamp = 987654321,
            picture = "",
            adresse = "Test address 2",
            cordinateGps = Coordinate(2.2, 3.3),
            profilPicture = ""
        )

        api.add(event1).first()
        api.add(event2).first()

        val fetchedEvents = api.getAllEvent("Test Title", true).first()

        assertTrue(fetchedEvents.contains(event1))
        assertTrue(fetchedEvents.contains(event2))
    }
}
