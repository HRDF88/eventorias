package com.nedrysystems.eventorias.domain.useCase.user.container

import com.nedrysystems.eventorias.domain.useCase.user.useCase.GetCurrentUserUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.GetNotificationSettingUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.InsertCurrentUserUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.IsUserLoggedInUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.LoadUserUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.OnSignInResultUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.SetNotificationEnableUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.SignInUserUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.SignOutUserUseCase
import javax.inject.Inject


/**
 * A data class that encapsulates all the use cases related to user management.
 *
 * This class provides a single point of access for all user-related operations,
 * such as signing in, signing out, checking if a user is logged in, and updating user settings.
 * It groups various use case classes that handle specific user-related tasks.
 *
 * @property onSignInResult The use case for handling the result of a sign-in attempt.
 * @property signIn The use case for signing in a user.
 * @property signOut The use case for signing out a user.
 * @property getCurrentUser The use case for retrieving the current user.
 * @property isUserLoggedIn The use case for checking if a user is logged in.
 * @property setNotificationEnable The use case for enabling or disabling notifications for the user.
 * @property insertCurrentUser The use case for inserting a new user into the system.
 * @property loadUser The use case for loading the user data.
 * @property getNotificationSetting The use case for retrieving the user's notification settings.
 *
 * @constructor Creates a new instance of [UserUseCases] by injecting all the necessary dependencies for user management.
 */
data class UserUseCases @Inject constructor(
    val onSignInResult: OnSignInResultUseCase,
    val signIn: SignInUserUseCase,
    val signOut: SignOutUserUseCase,
    val getCurrentUser: GetCurrentUserUseCase,
    val isUserLoggedIn: IsUserLoggedInUseCase,
    val setNotificationEnable: SetNotificationEnableUseCase,
    val insertCurrentUser: InsertCurrentUserUseCase,
    val loadUser: LoadUserUseCase,
    val getNotificationSetting: GetNotificationSettingUseCase

)