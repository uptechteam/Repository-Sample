package com.uptech.repositorysample.data.di

import android.content.Context
import com.uptech.repositorysample.data.UserManager
import com.uptech.repositorysample.data.balance.BalanceApi
import com.uptech.repositorysample.data.balance.BalanceCache
import com.uptech.repositorysample.data.items.ItemApi
import com.uptech.repositorysample.data.items.ItemCache
import com.uptech.repositorysample.dataStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

  companion object {
    @DataScope
    @Provides
    fun providesUserManager(
      context: Context
    ): UserManager = UserManager(context.dataStore)

    val provideBalanceCache: BalanceCache
      @DataScope
      @Provides
      get() = BalanceCache()

    val provideItemCache: ItemCache
      @DataScope
      @Provides
      get() = ItemCache()

    @DataScope
    @Provides
    fun provideBalanceApi(userManager: UserManager): BalanceApi = BalanceApi(userManager)

    @DataScope
    @Provides
    fun providesItemApi(userManager: UserManager): ItemApi = ItemApi(userManager)
  }
}
