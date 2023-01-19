package com.uptech.repositorysample.data.di

import com.uptech.repositorysample.data.balance.BalanceRepository
import com.uptech.repositorysample.data.items.ItemRepository
import dagger.Component

@RepoScope
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

  companion object {
    fun get(dataSourceComponent: DataSourceComponent) =
      DaggerRepositoryComponent.builder()
        .dataSourceComponent(dataSourceComponent)
        .build()
  }
}