package com.nedrysystems.eventorias.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger Hilt module for providing application dependencies.
 * This module defines how the application's dependencies, such as database, DAOs, and repositories,
 * are provided to the application components.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

}