package com.nedrysystems.eventorias.data.webService.firebase

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.nedrysystems.eventorias.data.webService.serviceInterface.EventApi
import com.nedrysystems.eventorias.domain.mapper.toEvent
import com.nedrysystems.eventorias.domain.mapper.toFirestoreMap
import com.nedrysystems.eventorias.domain.model.Event
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class CollectionEventFirebaseAPI @Inject constructor(
    private val firestore: FirebaseFirestore
) : EventApi {

    private val eventCollection = firestore.collection("Event")

    override fun add(event: Event): Flow<Event> = callbackFlow {
        try {
            val docRef = eventCollection.document(event.id)
            docRef.set(event.toFirestoreMap()).await()
            trySend(event)
        } catch (e: Exception) {
            close(e)
        }
        awaitClose {  }
    }

    override fun getEventById(id: String): Flow<Event> = callbackFlow {
        val listener = eventCollection.document(id)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                snapshot?.toEvent()?.let { trySend(it) }
            }
        awaitClose { listener.remove() }
    }

    override fun getAllEvent(tittle: String, orderByTimestamp: Boolean?): Flow<List<Event>> =
        callbackFlow {
            var query = eventCollection
                .apply {
                    if (tittle.isNotBlank()) {
                        whereEqualTo("tittle", tittle) // Filtre uniquement si le titre est non vide
                    }
                }

            if (orderByTimestamp == true) {
                query = query.orderBy("timestamp") as CollectionReference
            }

            val listener = query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("CollectionEventFirebaseAPI", "Error fetching events: ${error.message}", error)
                    close(error)
                    return@addSnapshotListener
                }
                val events = snapshot?.documents?.mapNotNull { doc -> doc.toEvent() } ?: emptyList()
                trySend(events)
            }

            awaitClose {
                listener.remove()
                Log.d("CollectionEventFirebaseAPI", "Listener removed for events with title: $tittle")
            }
        }


}
