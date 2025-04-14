package com.nedrysystems.eventorias.data.webService.serviceInterface

import com.nedrysystems.eventorias.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventApi {
    fun add(event : Event) : Flow<Event>

    fun getEventById(id : String) : Flow<Event>

    fun getAllEvent(tittle : String, orderByTimestamp : Boolean?) : Flow<Event>
}