package com.uptech.repositorysample.login

import com.uptech.repositorysample.ActivityScope
import com.uptech.repositorysample.data.UserManager
import com.uptech.repositorysample.login.LoginViewModel.Event
import com.uptech.repositorysample.main.di.authenticated.AuthenticatedComponentHolder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

@Module
object LoginModule {
  val events: Channel<Event>
    @ActivityScope
    @Provides
    get() = Channel()

  @ActivityScope
  @Provides
  fun provideLoginInteractor(
    userManager: UserManager,
    authenticatedComponentHolder: AuthenticatedComponentHolder
  ): LoginInteractor =
    LoginInteractor(
      userManager = userManager,
      authenticatedComponentHolder = authenticatedComponentHolder
    )

  @ActivityScope
  @Provides
  fun events(events: Channel<Event>): Flow<Event> = events.receiveAsFlow()

  @ActivityScope
  @Provides
  fun provideFactory(
    events: Channel<Event>,
    loginInteractor: LoginInteractor
  ): LoginViewModel.Factory =
    LoginViewModel.Factory(
      events = events,
      loginInteractor = loginInteractor
    )
}