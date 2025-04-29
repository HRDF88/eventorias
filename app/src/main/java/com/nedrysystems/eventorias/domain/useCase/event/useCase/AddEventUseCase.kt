package com.nedrysystems.eventorias.domain.useCase.event.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.EventRepositoryInterface
import com.nedrysystems.eventorias.domain.model.Event
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * A use case class for adding a new event to the repository.
 *
 * This class is responsible for invoking the repository method to add a new event to the data source.
 * It wraps the call to the repository and exposes it as a function that returns a [Flow] of the added event.
 *
 * @property repository The repository interface that manages event data.
 *
 * @constructor Creates a new instance of [AddEventUseCase] by injecting the repository dependency.
 */
class AddEventUseCase @Inject constructor(
    private val repository: EventRepositoryInterface
) {

    /**
     * Invokes the repository method to add the given event.
     *
     * This function passes the event to the repository's [addEvent] method and returns a [Flow] of the event.
     *
     * @param event The event to be added.
     * @return A [Flow] that emits the added event.
     */
    operator fun invoke(event: Event): Flow<Event> {
        return repository.addEvent(event)
    }
}