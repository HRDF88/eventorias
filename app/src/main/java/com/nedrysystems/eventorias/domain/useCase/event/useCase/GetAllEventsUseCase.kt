package com.nedrysystems.eventorias.domain.useCase.event.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.EventRepositoryInterface
import com.nedrysystems.eventorias.domain.model.Event
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * A use case class for retrieving all events from the repository.
 *
 * This class is responsible for invoking the repository method to get all events, optionally filtered by title and ordered by timestamp.
 * It wraps the call to the repository and exposes it as a function that returns a [Flow] of the list of events.
 *
 * @property repository The repository interface that manages event data.
 *
 * @constructor Creates a new instance of [GetAllEventsUseCase] by injecting the repository dependency.
 */
class GetAllEventsUseCase @Inject constructor(
    private val repository: EventRepositoryInterface
) {

    /**
     * Invokes the repository method to retrieve events.
     *
     * This function passes the title filter and order by timestamp flag to the repository's [getAllEvents] method
     * and returns a [Flow] of a list of events that match the criteria.
     *
     * @param tittle The title to filter events by. If empty, no filtering is applied.
     * @param orderByTimestamp A flag to determine whether the events should be ordered by timestamp.
     *                          If null, no specific ordering is applied.
     * @return A [Flow] that emits a list of events matching the given criteria.
     */
    operator fun invoke(tittle: String, orderByTimestamp: Boolean? = null): Flow<List<Event>> {
        return repository.getAllEvents(tittle, orderByTimestamp)
    }
}