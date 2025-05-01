package com.nedrysystems.eventorias.data

import com.nedrysystems.eventorias.data.webService.serviceInterface.EventApi
import com.nedrysystems.eventorias.domain.model.Coordinate
import com.nedrysystems.eventorias.domain.model.Event
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.Test


class EventApiTest {

    @Mock
    private lateinit var eventApi: EventApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `add should emit the added event`(): Unit = runBlocking {
        val event = Event(
            id = "e1",
            tittle = "Fete de Jocelyn Testing",
            description = "c'est la fête chez Jocelyn!",
            timestamp = 1680000000000,
            picture = "https://example.com/pic.jpg",
            adresse = "1 rue de Testing, 75000 Paris",
            cordinateGps = Coordinate(48.8566, 2.3522),
            profilPicture = "https://example.com/profile.jpg"
        )

        `when`(eventApi.add(event)).thenReturn(flowOf(event))

        val result = eventApi.add(event).first()

        assertEquals(event, result)
        verify(eventApi).add(event)
    }

    @Test
    fun `getEventById should emit the event with given id`(): Unit = runBlocking {
        val event = Event(
            id = "e2",
            tittle = "Fete de Jocelyn Testing",
            description = "c'est la fête chez Jocelyn!",
            timestamp = 1680000000000,
            picture = "https://example.com/pic.jpg",
            adresse = "1 rue de Testing, 75000 Paris",
            cordinateGps = Coordinate(48.8566, 2.3522),
            profilPicture = "https://example.com/profile.jpg"
        )

        `when`(eventApi.getEventById("e2")).thenReturn(flowOf(event))

        val result = eventApi.getEventById("e2").first()

        assertEquals(event, result)
        verify(eventApi).getEventById("e2")
    }

    @Test
    fun `getAllEvent should return list of matching events`(): Unit = runBlocking {
        val events = listOf(
            Event(
                id = "e3",
                tittle = "Fete de Jocelyn Testing",
                description = "c'est la fête chez Jocelyn!",
                timestamp = 1680000000000,
                picture = "https://example.com/pic.jpg",
                adresse = "1 rue de Testing, 75000 Paris",
                cordinateGps = Coordinate(48.8566, 2.3522),
                profilPicture = "https://example.com/profile.jpg"
            ),
            Event(
                id = "e4",
                tittle = "Fete de Jocelyn Testing",
                description = "c'est la fête chez Jocelyn!",
                timestamp = 1680000000000,
                picture = "https://example.com/pic.jpg",
                adresse = "1 rue de Testing, 75000 Paris",
                cordinateGps = Coordinate(48.8566, 2.3522),
                profilPicture = "https://example.com/profile.jpg"
            )
        )

        `when`(eventApi.getAllEvent("", true)).thenReturn(flowOf(events))

        val result = eventApi.getAllEvent("", true).first()

        assertEquals(events, result)
        verify(eventApi).getAllEvent("", true)
    }
}
