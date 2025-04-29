package com.nedrysystems.eventorias.utils.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.nedrysystems.eventorias.utils.serviceInterface.FCMSubscriptionManager

/**
 * Manages Firebase Cloud Messaging (FCM) subscription for notifications.
 *
 * This object handles subscribing and unsubscribing to a specific FCM topic to receive push notifications.
 * The topic used is the default channel for notifications (`FCM_DEFAULT_CHANNEL`).
 *
 * It interacts with Firebase's messaging service to allow users to receive notifications based on
 * the topic subscription status.
 */
object MyFirebaseMessagingServiceSubscribe : FCMSubscriptionManager {

    private const val TOPIC_NEWS = "FCM_DEFAULT_CHANNEL"

    /**
     * Subscribes the device to the default notification topic.
     *
     * This method subscribes the device to a topic used for general notifications (e.g., "FCM_DEFAULT_CHANNEL").
     * On success, a log message is displayed confirming the subscription. If an error occurs,
     * it logs the failure and provides an exception message.
     */
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

    /**
     * Unsubscribes the device from the default notification topic.
     *
     * This method unsubscribes the device from the topic used for general notifications.
     * On success, a log message is displayed confirming the unsubscription. If an error occurs,
     * it logs the failure and provides an exception message.
     */
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
