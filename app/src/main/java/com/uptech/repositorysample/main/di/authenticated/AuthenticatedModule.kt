package com.uptech.repositorysample.main.di.authenticated

import com.uptech.repositorysample.AuthenticatedScope
import com.uptech.repositorysample.LogoutInteractor
import com.uptech.repositorysample.data.UserManager
import com.uptech.repositorysample.data.balance.BalanceApi
import com.uptech.repositorysample.data.balance.BalanceCache
import com.uptech.repositorysample.data.balance.BalanceRepository
import com.uptech.repositorysample.data.items.ItemApi
import com.uptech.repositorysample.data.items.ItemCache
import com.uptech.repositorysample.data.items.ItemRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

@Module
object AuthenticatedModule {
  val authenticatedJob: Job
    @AuthenticatedScope
    @Provides
    get() = SupervisorJob()

  @AuthenticatedScope
  @Provides
  fun provideAuthenticatedScope(
    dispatcher: CoroutineDispatcher,
    authenticatedJob: Job
  ): CoroutineScope = CoroutineScope(authenticatedJob + dispatcher)

  @AuthenticatedScope
  @Provides
  fun provideItemRepository(
    itemApi: ItemApi,
    itemCache: ItemCache,
    balanceApi: BalanceApi,
    balanceCache: BalanceCache,
    authenticatedScope: CoroutineScope
  ): ItemRepository = ItemRepository(
    itemApi, itemCache, balanceApi, balanceCache, authenticatedScope
  )

  @AuthenticatedScope
  @Provides
  fun provideBalanceRepository(
    api: BalanceApi,
    balanceCache: BalanceCache,
    authenticatedScope: CoroutineScope
  ): BalanceRepository = BalanceRepository(
    api = api,
    balanceCache = balanceCache,
    authenticatedScope = authenticatedScope
  )

  @AuthenticatedScope
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
}