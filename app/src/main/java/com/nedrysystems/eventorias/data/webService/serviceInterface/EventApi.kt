package com.nedrysystems.eventorias.data.webService.serviceInterface

import com.nedrysystems.eventorias.domain.model.Event
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines the contract for event-related operations with a remote data source (e.g., Firebase Firestore).
 */
interface EventApi {

    /**
     * Adds a new event to the data source.
     *
     * @param event The [Event] object to be added.
     * @return A [Flow] that emits the added [Event] when the operation is complete.
     */
    fun add(event: Event): Flow<Event>

    /**
     * Retrieves a single event by its unique identifier.
     *
     * @param id The ID of the event to retrieve.
     * @return A [Flow] that emits the corresponding [Event] when found or updated.
     */
    fun getEventById(id: String): Flow<Event>

    /**
     * Retrieves a list of events optionally filtered by title and ordered by timestamp.
     *
     * @param tittle The title to filter events by. If blank, all events are returned.
     * @param orderByTimestamp If true, the events will be ordered by their timestamp field.
     * @return A [Flow] that emits a list of [Event] objects matching the criteria.
     */
    fun getAllEvent(tittle: String, orderByTimestamp: Boolean?): Flow<List<Event>>
}