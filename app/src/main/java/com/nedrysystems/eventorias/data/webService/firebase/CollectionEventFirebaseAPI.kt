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

/**
 * Firebase implementation of [EventApi] using Firestore as the backend.
 *
 * This class handles adding, retrieving, and observing event documents in a Firestore collection.
 *
 * @property firestore The [FirebaseFirestore] instance used to access Firestore.
 */
class CollectionEventFirebaseAPI @Inject constructor(
    private val firestore: FirebaseFirestore
) : EventApi {

    private val eventCollection = firestore.collection("Event")


    /**
     * Adds a new [Event] to Firestore.
     *
     * @param event The [Event] to add.
     * @return A [Flow] that emits the added [Event] once it has been saved.
     */
    override fun add(event: Event): Flow<Event> = callbackFlow {
        try {
            val docRef = eventCollection.document(event.id)
            docRef.set(event.toFirestoreMap()).await()
            trySend(event)
        } catch (e: Exception) {
            close(e)
        }
        awaitClose { }
    }

    /**
     * Retrieves a single [Event] by its unique ID.
     *
     * @param id The ID of the event to retrieve.
     * @return A [Flow] that emits the [Event] each time it is updated in Firestore.
     */
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

    /**
     * Retrieves all [Event]s that match a given title and optionally orders them by timestamp.
     *
     * @param tittle The title to filter by. If blank, no filtering is applied.
     * @param orderByTimestamp Whether to order results by timestamp. Defaults to `null`.
     * @return A [Flow] that emits the list of matching [Event]s and updates when data changes.
     */
    override fun getAllEvent(tittle: String, orderByTimestamp: Boolean?): Flow<List<Event>> =
        callbackFlow {
            var query = eventCollection
                .apply {
                    if (tittle.isNotBlank()) {
                        whereEqualTo("tittle", tittle)
                    }
                }

            if (orderByTimestamp == true) {
                query = query.orderBy("timestamp") as CollectionReference
            }

            val listener = query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e(
                        "CollectionEventFirebaseAPI",
                        "Error fetching events: ${error.message}",
                        error
                    )
                    close(error)
                    return@addSnapshotListener
                }
                val events = snapshot?.documents?.mapNotNull { doc -> doc.toEvent() } ?: emptyList()
                trySend(events)
            }

            awaitClose {
                listener.remove()
                Log.d(
                    "CollectionEventFirebaseAPI",
                    "Listener removed for events with title: $tittle"
                )
            }
        }


}
