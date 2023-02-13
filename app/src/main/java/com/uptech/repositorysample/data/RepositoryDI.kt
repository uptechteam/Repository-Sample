package com.uptech.repositorysample.data

import com.uptech.repositorysample.data.balance.BalanceApi
import com.uptech.repositorysample.data.balance.BalanceCache
import com.uptech.repositorysample.data.balance.BalanceRepository
import com.uptech.repositorysample.data.di.DataSourceComponent
import com.uptech.repositorysample.data.items.ItemApi
import com.uptech.repositorysample.data.items.ItemCache
import com.uptech.repositorysample.data.items.ItemRepository
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.BINARY

@RepositoryScope
@Component(
  dependencies = [DataSourceComponent::class],
  modules = [RepositoryModule::class]
)
interface RepositoryComponent {
  val itemRepository: ItemRepository
  val balanceRepository: BalanceRepository

  @Component.Builder
  interface Builder {
    fun dataSourceComponent(component: DataSourceComponent): Builder
    fun build(): RepositoryComponent
  }
}

@Module
object RepositoryModule {

  @RepositoryScope
  @Provides
  fun provideItemRepository(
    itemApi: ItemApi,
    itemCache: ItemCache,
    balanceApi: BalanceApi,
    balanceCache: BalanceCache
  ): ItemRepository = ItemRepository(
    itemApi = itemApi,
    itemCache = itemCache,
    balanceApi = balanceApi,
    balanceCache = balanceCache
  )


  @RepositoryScope
  @Provides
  fun provideBalanceRepository(
    api: BalanceApi,
    balanceCache: BalanceCache
  ): BalanceRepository = BalanceRepository(
    api = api,
    balanceCache = balanceCache
  )
}

@Scope
@Retention(BINARY)
annotation class RepositoryScope