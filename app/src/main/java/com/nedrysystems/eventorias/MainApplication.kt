package com.nedrysystems.eventorias

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The application class for the app, annotated with @HiltAndroidApp to trigger Hilt's code generation.
 * This class serves as the entry point for the app and sets up Hilt's dependency injection framework.
 */
@HiltAndroidApp
class MainApplication : Application()