package com.uptech.repositorysample.main.di.authenticated

import com.uptech.repositorysample.AuthenticatedScope
import com.uptech.repositorysample.data.balance.BalanceDataBridge
import com.uptech.repositorysample.data.balance.BalanceRepository
import com.uptech.repositorysample.data.items.ItemDataBridge
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
  fun provideItemContext(
    itemRepository: ItemRepository,
    authenticatedScope: CoroutineScope
  ): ItemDataBridge = ItemDataBridge(
    itemRepository = itemRepository,
    authenticatedScope = authenticatedScope
  )

  @AuthenticatedScope
  @Provides
  fun provideBalanceContext(
    balanceRepository: BalanceRepository,
    authenticatedScope: CoroutineScope
  ): BalanceDataBridge = BalanceDataBridge(
    balanceRepository = balanceRepository,
    authenticatedScope = authenticatedScope
  )
}