package com.uptech.repositorysample.data.di

import com.uptech.repositorysample.data.balance.BalanceApi
import com.uptech.repositorysample.data.balance.BalanceCache
import com.uptech.repositorysample.data.balance.BalanceRepository
import com.uptech.repositorysample.data.items.ItemApi
import com.uptech.repositorysample.data.items.ItemCache
import com.uptech.repositorysample.data.items.ItemRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

  companion object {
    @RepoScope
    @Provides
    fun provideItemRepository(
      itemApi: ItemApi,
      itemCache: ItemCache,
      balanceApi: BalanceApi,
      balanceCache: BalanceCache
    ): ItemRepository =
      ItemRepository(
        itemApi = itemApi,
        itemCache = itemCache,
        balanceApi = balanceApi,
        balanceCache = balanceCache
      )

    @RepoScope
    @Provides
    fun provideBalanceRepository(
      api: BalanceApi,
      balanceCache: BalanceCache
    ): BalanceRepository = BalanceRepository(
      api = api,
      balanceCache = balanceCache
    )
  }
}