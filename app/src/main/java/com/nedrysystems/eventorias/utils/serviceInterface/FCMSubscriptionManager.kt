package com.nedrysystems.eventorias.utils.serviceInterface

/**
 * Interface for managing Firebase Cloud Messaging (FCM) subscriptions.
 *
 * This interface defines the contract for subscribing and unsubscribing to FCM topics.
 * Implementations of this interface will handle the logic for managing the subscription status
 * of a device to receive notifications from Firebase.
 */
interface FCMSubscriptionManager {

    /**
     * Subscribes the device to receive notifications.
     *
     * This method will initiate the process of subscribing the device to a specific FCM topic.
     * Implementations should define which topic to subscribe to.
     */
    fun subscribeToNotifications()

    /**
     * Unsubscribes the device from receiving notifications.
     *
     * This method will initiate the process of unsubscribing the device from the currently subscribed
     * FCM topic. Implementations should handle the unsubscription logic.
     */
    fun unsubscribeFromNotifications()
}