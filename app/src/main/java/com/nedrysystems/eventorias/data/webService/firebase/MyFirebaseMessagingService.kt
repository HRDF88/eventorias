package com.nedrysystems.eventorias.data.webService.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nedrysystems.eventorias.MainActivity
import com.nedrysystems.eventorias.R

/**
 * Firebase service class that handles receiving and processing Firebase Cloud Messaging (FCM) notifications.
 * It also manages the generation of new tokens when the app is installed or the token is refreshed.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    val fireBaseMessaging = FirebaseMessaging.getInstance()

    /**
     * Called when a new token is generated or refreshed.
     * This method is triggered when the FCM token is updated.
     *
     * @param token The new FCM token.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Nouveau token : $token")

        sendTokenToServer(token)
    }

    /**
     * Called when a new token is generated or refreshed.
     * This method is triggered when the FCM token is updated.
     *
     * @param token The new FCM token.
     */
    private fun sendTokenToServer(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Nouveau token : $token")


    }

    /**
     * Sends the FCM token to the server for storage.
     * This can be used to associate the token with a user's account or for other server-side processing.
     *
     * @param token The new FCM token to be sent to the server.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "Message reçu data=${remoteMessage.data}")

        val isNotificationsEnabled = isNotificationEnabledLocally()

        if (isNotificationsEnabled) {
            remoteMessage.notification?.let {
                showNotification(it.title ?: "Notification", it.body ?: "")
            }
        } else {
            Log.d("FCM", "Notifications désactivées, pas de notification affichée.")
        }
    }

    /**
     * Function to read the notification status from the local preferences.
     */
    private fun isNotificationEnabledLocally(): Boolean {
        val prefs = applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return prefs.getBoolean("notifications_enabled", true)
    }

    /**
     * Displays the notification to the user.
     * This method builds and shows the notification using Android's NotificationManager.
     *
     * @param title The title of the notification.
     * @param message The body content of the notification.
     */
    private fun showNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
        )

        val channelId = "FCM_DEFAULT_CHANNEL"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}
