package com.nedrysystems.eventorias.utils.accessibility

import android.content.Context
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager

/**
 * Utility object responsible for announcing messages to the accessibility service.
 *
 * This object helps send custom messages to the accessibility manager for users who rely on screen readers or other accessibility tools.
 * It checks if the accessibility service is enabled before sending the message.
 */
object AccessibilityAnnouncer {

    /**
     * Announces a message to the accessibility service.
     *
     * This function sends the provided message to the system's accessibility manager, which will be read out by screen readers
     * or other accessibility tools if they are enabled.
     *
     * @param context The context of the current application or activity.
     * @param message The message to be announced by the accessibility service.
     */
    fun announce(context: Context, message: String) {
        val accessibilityManager =
            context.getSystemService(AccessibilityManager::class.java)
        if (accessibilityManager?.isEnabled == true) {
            val event = AccessibilityEvent.obtain().apply {
                eventType = AccessibilityEvent.TYPE_ANNOUNCEMENT
                className = context.javaClass.name
                packageName = context.packageName
                text.add(message)
            }
            accessibilityManager.sendAccessibilityEvent(event)
        }
    }
}