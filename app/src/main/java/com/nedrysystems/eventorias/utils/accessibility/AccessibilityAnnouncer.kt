package com.nedrysystems.eventorias.utils.accessibility

import android.content.Context
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager

object AccessibilityAnnouncer {
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