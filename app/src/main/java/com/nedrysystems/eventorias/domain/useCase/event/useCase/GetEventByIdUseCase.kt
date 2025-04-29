package com.nedrysystems.eventorias.domain.useCase.event.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.EventRepositoryInterface
import com.nedrysystems.eventorias.domain.model.Event
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * A use case class for retrieving a specific event by its ID from the repository.
 *
 * This class is responsible for invoking the repository method to get an event by its ID.
 * It wraps the call to the repository and exposes it as a function that returns a [Flow] of a single event.
 *
 * @property repository The repository interface that manages event data.
 *
 * @constructor Creates a new instance of [GetEventByIdUseCase] by injecting the repository dependency.
 */
class GetEventByIdUseCase @Inject constructor(
    private val repository: EventRepositoryInterface
) {

    /**
     * Invokes the repository method to retrieve a specific event by its ID.
     *
     * This function calls the repository's [getEventById] method to get the event with the specified ID.
     * It returns a [Flow] that emits the event with the given ID when available.
     *
     * @param id The unique identifier of the event to be fetched.
     * @return A [Flow] that emits the event with the specified ID.
     */
    operator fun invoke(id: String): Flow<Event> {
        return repository.getEventById(id)
    }
}