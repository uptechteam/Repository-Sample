package com.uptech.repositorysample.data.di

import android.content.Context
import com.uptech.repositorysample.data.UserManager
import com.uptech.repositorysample.data.balance.BalanceApi
import com.uptech.repositorysample.data.balance.BalanceCache
import com.uptech.repositorysample.data.items.ItemApi
import com.uptech.repositorysample.data.items.ItemCache
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@DataScope
@Component(
  modules = [DataSourceModule::class]
)
interface DataSourceComponent {
  val userManager: UserManager
  val balanceCache: BalanceCache
  val itemCache: ItemCache
  val balanceApi: BalanceApi
  val itemApi: ItemApi

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun build(): DataSourceComponent
  }
}
