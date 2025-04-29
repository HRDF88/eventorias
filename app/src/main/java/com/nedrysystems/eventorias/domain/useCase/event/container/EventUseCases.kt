package com.nedrysystems.eventorias.domain.useCase.event.container

import com.nedrysystems.eventorias.domain.useCase.event.useCase.AddEventUseCase
import com.nedrysystems.eventorias.domain.useCase.event.useCase.GetAllEventsUseCase
import com.nedrysystems.eventorias.domain.useCase.event.useCase.GetEventByIdUseCase
import javax.inject.Inject

/**
 * A data class that groups all the use cases related to events.
 *
 * This class is responsible for managing event-related operations, such as adding a new event,
 * fetching an event by its ID, and retrieving all events based on certain filters.
 *
 * @property addEvent The use case for adding a new event.
 * @property getEventById The use case for retrieving an event by its unique ID.
 * @property getAllEvents The use case for retrieving all events, optionally filtered by title and ordered by timestamp.
 *
 * @constructor Creates a new [EventUseCases] object by injecting the required use case dependencies.
 */
data class EventUseCases @Inject constructor(
    val addEvent: AddEventUseCase,
    val getEventById: GetEventByIdUseCase,
    val getAllEvents: GetAllEventsUseCase
)