package com.nedrysystems.eventorias.data.repository

import com.nedrysystems.eventorias.data.repositoryInterface.EventRepositoryInterface
import com.nedrysystems.eventorias.data.webService.serviceInterface.EventApi
import com.nedrysystems.eventorias.domain.model.Event
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of [EventRepositoryInterface] that communicates with a remote [EventApi].
 *
 * This repository is responsible for managing event data by delegating operations
 * such as adding, retrieving, and listing events to the API layer.
 *
 * @property eventApi The API service used to interact with the backend.
 */
class EventRepository @Inject constructor(private val eventApi: EventApi) :
    EventRepositoryInterface {

    /**
     * Adds a new event using the remote API.
     *
     * @param event The [Event] to be added.
     * @return A [Flow] emitting the added [Event].
     */
    override fun addEvent(event: Event): Flow<Event> {
        return eventApi.add(event)
    }

    /**
     * Retrieves a single event by its ID from the remote API.
     *
     * @param id The unique identifier of the event.
     * @return A [Flow] emitting the [Event] corresponding to the given ID.
     */
    override fun getEventById(id: String): Flow<Event> {
        return eventApi.getEventById(id)
    }

    /**
     * Retrieves a list of events filtered by title and optionally ordered by timestamp.
     *
     * @param tittle A string to filter events by title (case-insensitive).
     * @param orderByTimestamp If true, events are ordered by descending timestamp;
     *                         if false, by ascending timestamp; if null, default order is used.
     * @return A [Flow] emitting a list of [Event]s.
     */
    override fun getAllEvents(tittle: String, orderByTimestamp: Boolean?): Flow<List<Event>> {
        return eventApi.getAllEvent(tittle, orderByTimestamp)
    }
}