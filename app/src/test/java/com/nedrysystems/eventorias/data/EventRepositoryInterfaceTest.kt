package com.nedrysystems.eventorias.data

import com.nedrysystems.eventorias.data.repository.EventRepository
import com.nedrysystems.eventorias.data.repositoryInterface.EventRepositoryInterface
import com.nedrysystems.eventorias.data.webService.serviceInterface.EventApi
import com.nedrysystems.eventorias.domain.model.Coordinate
import com.nedrysystems.eventorias.domain.model.Event
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class EventRepositoryInterfaceTest {

    private lateinit var mockApi: EventApi
    private lateinit var repository: EventRepositoryInterface

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockApi = mock(EventApi::class.java)
        repository = EventRepository(mockApi)
    }

    private fun createFakeEvent(id: String = "1") = Event(
        id = id,
        tittle = "Fete de Jocelyn Testing",
        description = "c'est la fête chez Jocelyn!",
        timestamp = 1680000000000,
        picture = "https://example.com/pic.jpg",
        adresse = "1 rue de Testing, 75000 Paris",
        cordinateGps = Coordinate(48.8566, 2.3522),
        profilPicture = "https://example.com/profile.jpg"
    )

    @Test
    fun `addEvent returns the expected event`(): Unit = runBlocking {
        val event = createFakeEvent()
        `when`(mockApi.add(event)).thenReturn(flowOf(event))

        val result = repository.addEvent(event).toList()

        assertEquals(listOf(event), result)
        verify(mockApi).add(event)
    }

    @Test
    fun `getEventById returns the correct event`(): Unit = runBlocking {
        val event = createFakeEvent("42")
        `when`(mockApi.getEventById("42")).thenReturn(flowOf(event))

        val result = repository.getEventById("42").toList()

        assertEquals(listOf(event), result)
        verify(mockApi).getEventById("42")
    }

    @Test
    fun `getAllEvents returns filtered events list`(): Unit = runBlocking {
        val events = listOf(
            createFakeEvent("1"),
            createFakeEvent("2")
        )
        `when`(mockApi.getAllEvent("Fete de Jocelyn Testing", true)).thenReturn(flowOf(events))

        val result = repository.getAllEvents("Fete de Jocelyn Testing", true).first()

        assertEquals(events, result)
        verify(mockApi).getAllEvent("Fete de Jocelyn Testing", true)
    }
}