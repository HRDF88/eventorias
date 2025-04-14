package com.nedrysystems.eventorias.domain.useCase.event.container

import com.nedrysystems.eventorias.domain.useCase.event.useCase.AddEventUseCase
import com.nedrysystems.eventorias.domain.useCase.event.useCase.GetAllEventsUseCase
import com.nedrysystems.eventorias.domain.useCase.event.useCase.GetEventByIdUseCase
import javax.inject.Inject

data class EventUseCases @Inject constructor(
    val addEvent: AddEventUseCase,
    val getEventById: GetEventByIdUseCase,
    val getAllEvents: GetAllEventsUseCase
)