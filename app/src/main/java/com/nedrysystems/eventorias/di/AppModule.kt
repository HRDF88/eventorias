package com.nedrysystems.eventorias.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.nedrysystems.eventorias.data.repository.EventRepository
import com.nedrysystems.eventorias.data.repository.UserRepository
import com.nedrysystems.eventorias.data.repositoryInterface.EventRepositoryInterface
import com.nedrysystems.eventorias.data.repositoryInterface.UserRepositoryInterface
import com.nedrysystems.eventorias.data.webService.firebase.CollectionEventFirebaseAPI
import com.nedrysystems.eventorias.data.webService.firebase.FirebaseUserService
import com.nedrysystems.eventorias.data.webService.firebase.MyFirebaseMessagingService
import com.nedrysystems.eventorias.data.webService.serviceInterface.EventApi
import com.nedrysystems.eventorias.data.webService.serviceInterface.UserApi
import com.nedrysystems.eventorias.domain.useCase.event.container.EventUseCases
import com.nedrysystems.eventorias.domain.useCase.event.useCase.AddEventUseCase
import com.nedrysystems.eventorias.domain.useCase.event.useCase.GetAllEventsUseCase
import com.nedrysystems.eventorias.domain.useCase.event.useCase.GetEventByIdUseCase
import com.nedrysystems.eventorias.domain.useCase.user.container.UserUseCases
import com.nedrysystems.eventorias.domain.useCase.user.useCase.GetCurrentUserUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.InsertCurrentUserUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.IsUserLoggedInUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.LoadUserUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.OnSignInResultUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.SetNotificationEnableUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.SignInUserUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.SignOutUserUseCase
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
    fun provideAuthApi(): UserApi {
        return FirebaseUserService()
    }

    @Provides
    @Singleton
    fun provideEventApi(): EventApi {
        return CollectionEventFirebaseAPI(Firebase.firestore)
    }

    @Provides
    fun provideUserRepository(userApi: UserApi): UserRepositoryInterface {
        return UserRepository(userApi)
    }

    @Provides
    fun provideUserUseCases(repository: UserRepositoryInterface): UserUseCases {
        return UserUseCases(
            onSignInResult = OnSignInResultUseCase(repository),
            signIn = SignInUserUseCase(repository),
            signOut = SignOutUserUseCase(repository),
            getCurrentUser = GetCurrentUserUseCase(repository),
            isUserLoggedIn = IsUserLoggedInUseCase(repository),
            setNotificationEnable = SetNotificationEnableUseCase(repository),
            insertCurrentUser = InsertCurrentUserUseCase(repository),
            loadUser = LoadUserUseCase(repository),

            )
    }

    @Provides
    fun provideEventRepository(eventApi: EventApi): EventRepositoryInterface {
        return EventRepository(eventApi)
    }

    @Provides
    fun provideEventUseCases(repository : EventRepositoryInterface) : EventUseCases {
        return EventUseCases(
            addEvent = AddEventUseCase(repository),
            getEventById = GetEventByIdUseCase(repository),
            getAllEvents = GetAllEventsUseCase(repository)

        )
    }

    @Provides
    fun provideFirebaseMessagingService(): MyFirebaseMessagingService {
        return MyFirebaseMessagingService()
    }

}