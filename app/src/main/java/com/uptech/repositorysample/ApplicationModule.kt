package com.uptech.repositorysample

import com.uptech.repositorysample.data.UserManager
import com.uptech.repositorysample.data.balance.BalanceRepository
import com.uptech.repositorysample.data.items.ItemRepository
import com.uptech.repositorysample.main.di.authenticated.AuthenticatedComponentHolder
import com.uptech.repositorysample.main.di.core.CoreComponent
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class ApplicationModule {

  @ApplicationScope
  @Provides
  fun provideAuthenticatedComponentHolder(coreComponent: CoreComponent): AuthenticatedComponentHolder =
    AuthenticatedComponentHolder(coreComponent)

  @ApplicationScope
  @Provides
  fun provideLogoutInteractor(
    userManager: UserManager,
    itemRepository: ItemRepository,
    balanceRepository: BalanceRepository,
    authenticatedComponentHolder: AuthenticatedComponentHolder
  ): LogoutInteractor = LogoutInteractor(
    userManager,
    itemRepository,
    balanceRepository,
    authenticatedComponentHolder
  )

  @ApplicationScope
  @Provides
  fun provideCoroutineDispatcher(): CoroutineDispatcher =
    Dispatchers.IO
}