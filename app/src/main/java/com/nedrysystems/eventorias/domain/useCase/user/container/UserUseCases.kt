package com.nedrysystems.eventorias.domain.useCase.user.container

import com.nedrysystems.eventorias.domain.useCase.user.useCase.GetCurrentUserUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.InsertCurrentUserUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.IsUserLoggedInUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.LoadUserUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.OnSignInResultUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.SetNotificationEnableUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.SignInUserUseCase
import com.nedrysystems.eventorias.domain.useCase.user.useCase.SignOutUserUseCase
import javax.inject.Inject


data class UserUseCases @Inject constructor(
    val onSignInResult: OnSignInResultUseCase,
    val signIn: SignInUserUseCase,
    val signOut: SignOutUserUseCase,
    val getCurrentUser: GetCurrentUserUseCase,
    val isUserLoggedIn: IsUserLoggedInUseCase,
    val setNotificationEnable: SetNotificationEnableUseCase,
    val insertCurrentUser: InsertCurrentUserUseCase,
    val loadUser: LoadUserUseCase,

)