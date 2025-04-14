package com.nedrysystems.eventorias.data.repository

import com.nedrysystems.eventorias.data.repositoryInterface.EventRepositoryInterface
import com.nedrysystems.eventorias.data.webService.serviceInterface.EventApi
import com.nedrysystems.eventorias.domain.model.Event
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventRepository @Inject constructor(private val eventApi: EventApi) :
    EventRepositoryInterface {
    override fun addEvent(event: Event): Flow<Event> {
        return eventApi.add(event)
    }

    override fun getEventById(id: String): Flow<Event> {
        return eventApi.getEventById(id)
    }

    override fun getAllEvents(tittle: String, orderByTimestamp: Boolean?): Flow<Event> {
        return eventApi.getAllEvent(tittle, orderByTimestamp)
    }
}