package com.nedrysystems.eventorias.domain

import com.nedrysystems.eventorias.data.repositoryInterface.EventRepositoryInterface
import com.nedrysystems.eventorias.domain.model.Coordinate
import com.nedrysystems.eventorias.domain.model.Event
import com.nedrysystems.eventorias.domain.useCase.event.useCase.GetEventByIdUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.Test

class GetEventByIdUseCaseTest {

    private lateinit var getEventByIdUseCase: GetEventByIdUseCase
    private val eventRepository: EventRepositoryInterface = mock()

    @Before
    fun setUp() {
        getEventByIdUseCase = GetEventByIdUseCase(eventRepository)
    }

    @Test
    fun `invoke should return event by id`() = runBlocking {
       
        val event = Event(
            id = "1",
            tittle = "Fete de Jocelyn Testing",
            description = "c'est la fête chez Jocelyn!",
            timestamp = 1880000000000,
            picture = "https://example.com/pic.jpg",
            adresse = "1 rue de Testing, 75000 Paris",
            cordinateGps = Coordinate(48.8566, 2.3522),
            profilPicture = "https://example.com/profile.jpg"
        )


        `when`(eventRepository.getEventById("1")).thenReturn(flowOf(event))

        val result = getEventByIdUseCase("1").first()

        assertEquals(event, result)
    }

    @Test
    fun `invoke should return null when event not found`(): Unit = runBlocking {

        `when`(eventRepository.getEventById("999")).thenReturn(emptyFlow())

        val result = getEventByIdUseCase("999").firstOrNull()

        assertEquals(null, result)
    }
}