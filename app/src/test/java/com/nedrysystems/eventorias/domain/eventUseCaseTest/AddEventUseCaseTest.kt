package com.nedrysystems.eventorias.domain

import com.nedrysystems.eventorias.data.repositoryInterface.EventRepositoryInterface
import com.nedrysystems.eventorias.domain.model.Coordinate
import com.nedrysystems.eventorias.domain.model.Event
import com.nedrysystems.eventorias.domain.useCase.event.useCase.AddEventUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

import kotlin.test.assertEquals

class AddEventUseCaseTest {

    private lateinit var addEventUseCase: AddEventUseCase
    private val eventRepository: EventRepositoryInterface = mock()


    @Before
    fun setUp() {
        addEventUseCase = AddEventUseCase(eventRepository)
    }

    @Test
    fun `invoke should return the added event`() = runTest {

        val event = Event(
            id = "1",
            tittle = "Fete de Jocelyn Testing",
            description = "c'est la fête chez Jocelyn!",
            timestamp = 1680000000000,
            picture = "https://example.com/pic.jpg",
            adresse = "1 rue de Testing, 75000 Paris",
            cordinateGps = Coordinate(48.8566, 2.3522),
            profilPicture = "https://example.com/profile.jpg"
        )

        `when`(eventRepository.addEvent(event)).thenReturn(flowOf(event))


        val result = addEventUseCase(event).first()


        assertEquals(event, result)
    }

    @Test
    fun `invoke should handle error gracefully`() = runTest {

        val event = Event(
            id = "1",
            tittle = "Fete de Jocelyn Testing",
            description = "c'est la fête chez Jocelyn!",
            timestamp = 1680000000000,
            picture = "https://example.com/pic.jpg",
            adresse = "1 rue de Testing, 75000 Paris",
            cordinateGps = Coordinate(48.8566, 2.3522),
            profilPicture = "https://example.com/profile.jpg"
        )


       `when`(eventRepository.addEvent(event)).thenThrow(RuntimeException("Error adding event"))

        try {

            addEventUseCase(event).first()
        } catch (e: Exception) {

            assert(e is RuntimeException)
            assertEquals("Error adding event", e.message)
        }
    }
}
