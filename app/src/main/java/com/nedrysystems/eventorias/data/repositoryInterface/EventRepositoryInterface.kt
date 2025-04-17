package com.nedrysystems.eventorias.data.repositoryInterface

import com.nedrysystems.eventorias.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepositoryInterface {
    fun addEvent(event: Event): Flow<Event>
    fun getEventById(id: String): Flow<Event>
    fun getAllEvents(tittle: String, orderByTimestamp: Boolean? = null): Flow<List<Event>>
}