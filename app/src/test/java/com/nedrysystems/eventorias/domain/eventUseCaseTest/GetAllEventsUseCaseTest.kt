package com.nedrysystems.eventorias.domain

import com.nedrysystems.eventorias.data.repositoryInterface.EventRepositoryInterface
import com.nedrysystems.eventorias.domain.model.Coordinate
import com.nedrysystems.eventorias.domain.model.Event
import com.nedrysystems.eventorias.domain.useCase.event.useCase.GetAllEventsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetAllEventsUseCaseTest {

    private lateinit var getAllEventsUseCase: GetAllEventsUseCase
    private val eventRepository: EventRepositoryInterface = mock()  // Création du mock

    @Before
    fun setUp() {
        getAllEventsUseCase = GetAllEventsUseCase(eventRepository)  // Injection du mock
    }

    @Test
    fun `invoke with title filter should return filtered events`() = runBlocking {

        val event1 = Event(
            id = "1",
            tittle = "Fete de Jocelyn Testing",
            description = "c'est la fête chez Jocelyn!",
            timestamp = 1680000000000,
            picture = "https://example.com/pic.jpg",
            adresse = "1 rue de Testing, 75000 Paris",
            cordinateGps = Coordinate(48.8566, 2.3522),
            profilPicture = "https://example.com/profile.jpg"
        )

        val event2 = Event(
            id = "2",
            tittle = "Fete de Jocelyn Testing",
            description = "c'est la fête chez Jocelyn!",
            timestamp = 1680000000000,
            picture = "https://example.com/pic.jpg",
            adresse = "1 rue de Testing, 75000 Paris",
            cordinateGps = Coordinate(48.8566, 2.3522),
            profilPicture = "https://example.com/profile.jpg"
        )


        val events = listOf(event1, event2)


        `when`(
            eventRepository.getAllEvents(
                "Fete de Jocelyn Testing",
                null
            )
        ).thenReturn(flowOf(events.filter { it.tittle.contains("Fete de Jocelyn Testing") }))


        val result = getAllEventsUseCase("Fete de Jocelyn Testing").first()


        assertEquals(listOf(event1, event2), result)
    }

    @Test
    fun `invoke without title filter should return all events`() = runBlocking {
        val event1 = Event(
            id = "1",
            tittle = "Fete de Jocelyn Testing",
            description = "c'est la fête chez Jocelyn!",
            timestamp = 1680000000000,
            picture = "https://example.com/pic.jpg",
            adresse = "1 rue de Testing, 75000 Paris",
            cordinateGps = Coordinate(48.8566, 2.3522),
            profilPicture = "https://example.com/profile.jpg"
        )

        val event2 = Event(
            id = "2",
            tittle = "Fete de Jocelyn Testing",
            description = "c'est la fête chez Jocelyn!",
            timestamp = 1680000000000,
            picture = "https://example.com/pic.jpg",
            adresse = "1 rue de Testing, 75000 Paris",
            cordinateGps = Coordinate(48.8566, 2.3522),
            profilPicture = "https://example.com/profile.jpg"
        )


        val events = listOf(event1, event2)


        `when`(eventRepository.getAllEvents("", null)).thenReturn(flowOf(events))


        val result = getAllEventsUseCase("", null).first()


        assertEquals(events, result)
    }

    @Test
    fun `invoke with orderByTimestamp should return ordered events`() = runBlocking {

        val event1 = Event(
            id = "1",
            tittle = "Fete de Jocelyn Testing",
            description = "c'est la fête chez Jocelyn!",
            timestamp = 1880000000000,
            picture = "https://example.com/pic.jpg",
            adresse = "1 rue de Testing, 75000 Paris",
            cordinateGps = Coordinate(48.8566, 2.3522),
            profilPicture = "https://example.com/profile.jpg"
        )

        val event2 = Event(
            id = "2",
            tittle = "Fete de Jocelyn Testing",
            description = "c'est la fête chez Jocelyn!",
            timestamp = 1680000000000,
            picture = "https://example.com/pic.jpg",
            adresse = "1 rue de Testing, 75000 Paris",
            cordinateGps = Coordinate(48.8566, 2.3522),
            profilPicture = "https://example.com/profile.jpg"
        )


        val events = listOf(event1, event2)


        `when`(
            eventRepository.getAllEvents(
                "",
                true
            )
        ).thenReturn(flowOf(events.sortedBy { it.timestamp }))


        val result = getAllEventsUseCase("", true).first()

        assertEquals(listOf(event2, event1), result)
    }
}
