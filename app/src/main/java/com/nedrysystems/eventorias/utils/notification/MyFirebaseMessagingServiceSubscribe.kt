package com.nedrysystems.eventorias.utils.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.nedrysystems.eventorias.utils.serviceInterface.FCMSubscriptionManager

object MyFirebaseMessagingServiceSubscribe : FCMSubscriptionManager{

    private const val TOPIC_NEWS = "news"

    override fun subscribeToNotifications() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_NEWS)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM_SUBSCRIBE", "Abonné au topic $TOPIC_NEWS")
                } else {
                    Log.e("FCM_SUBSCRIBE", "Échec de l'abonnement", task.exception)
                }
            }
    }

    override fun unsubscribeFromNotifications() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(TOPIC_NEWS)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FCM_UNSUBSCRIBE", "Désabonné du topic $TOPIC_NEWS")
                } else {
                    Log.e("FCM_UNSUBSCRIBE", "Échec du désabonnement", task.exception)
                }
            }
    }
}
