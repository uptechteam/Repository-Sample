package com.uptech.repositorysample.main.di

import com.uptech.repositorysample.ActivityScope
import com.uptech.repositorysample.LogoutInteractor
import com.uptech.repositorysample.data.UserManager
import com.uptech.repositorysample.data.balance.BalanceRepository
import com.uptech.repositorysample.data.items.ItemRepository
import com.uptech.repositorysample.main.MainViewModel
import com.uptech.repositorysample.main.MainViewModel.Event
import com.uptech.repositorysample.main.di.authenticated.AuthenticatedComponentHolder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

@Module
object MainModule {

  @get:[ActivityScope Provides] val events: Channel<Event> = Channel()

  @ActivityScope
  @Provides
  fun events(events: Channel<Event>): Flow<Event> = events.receiveAsFlow()

  @ActivityScope
  @Provides
  fun viewModelFactory(
    userManager: UserManager,
    events: Channel<Event>,
    logoutInteractor: LogoutInteractor,
    authenticatedComponentHolder: AuthenticatedComponentHolder
  ): MainViewModel.Factory =
    MainViewModel.Factory(
      userManager = userManager,
      events = events,
      logoutInteractor = logoutInteractor,
      authenticatedComponentHolder = authenticatedComponentHolder
    )

  @ActivityScope
  @Provides
  fun provideLogoutInteractor(
    userManager: UserManager,
    itemRepository: ItemRepository,
    balanceRepository: BalanceRepository,
    authenticatedComponentHolder: AuthenticatedComponentHolder
  ): LogoutInteractor = LogoutInteractor(
    userManager = userManager,
    itemRepository = itemRepository,
    balanceRepository = balanceRepository,
    authenticatedComponentHolder = authenticatedComponentHolder
  )
}