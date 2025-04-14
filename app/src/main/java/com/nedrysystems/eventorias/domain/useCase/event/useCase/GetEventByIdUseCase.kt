package com.nedrysystems.eventorias.domain.useCase.event.useCase

import com.nedrysystems.eventorias.data.repositoryInterface.EventRepositoryInterface
import com.nedrysystems.eventorias.domain.model.Event
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventByIdUseCase @Inject constructor(
    private val repository: EventRepositoryInterface
) {
    operator fun invoke(id: String): Flow<Event> {
        return repository.getEventById(id)
    }
}