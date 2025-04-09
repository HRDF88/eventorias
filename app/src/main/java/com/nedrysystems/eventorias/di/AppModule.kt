package com.nedrysystems.eventorias.di

import com.nedrysystems.eventorias.data.webService.firebase.FirebaseAuthService
import com.nedrysystems.eventorias.data.webService.serviceInterface.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing application dependencies.
 * This module defines how the application's dependencies, such as database, DAOs, and repositories,
 * are provided to the application components.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthApi() : AuthApi {
        return FirebaseAuthService()
    }

}