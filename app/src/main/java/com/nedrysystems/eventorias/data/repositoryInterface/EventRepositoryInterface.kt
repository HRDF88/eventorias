package com.nedrysystems.eventorias.data.repositoryInterface

import com.nedrysystems.eventorias.domain.model.Event
import kotlinx.coroutines.flow.Flow

/**
 * Interface that defines operations related to managing events in the repository.
 */
interface EventRepositoryInterface {

    /**
     * Adds a new [Event] to the data source.
     *
     * @param event The [Event] to be added.
     * @return A [Flow] emitting the added [Event], typically including any updates like assigned IDs.
     */
    fun addEvent(event: Event): Flow<Event>

    /**
     * Retrieves an [Event] by its unique identifier.
     *
     * @param id The unique ID of the event to fetch.
     * @return A [Flow] emitting the matching [Event].
     */
    fun getEventById(id: String): Flow<Event>

    /**
     * Retrieves a list of all [Event]s that match a specific title.
     *
     * @param tittle The title to filter events by.
     * @param orderByTimestamp Optional parameter to sort the results:
     *  - `true`: ascending order (chronological),
     *  - `false`: descending order (reverse chronological),
     *  - `null`: no specific ordering.
     * @return A [Flow] emitting a list of [Event]s.
     */
    fun getAllEvents(tittle: String, orderByTimestamp: Boolean? = null): Flow<List<Event>>
}