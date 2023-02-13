package com.uptech.repositorysample.main.di.authenticated

import com.uptech.repositorysample.AuthenticatedScope
import com.uptech.repositorysample.LogoutInteractor
import com.uptech.repositorysample.data.balance.BalanceRepository
import com.uptech.repositorysample.data.di.DataSourceComponent
import com.uptech.repositorysample.data.items.ItemRepository
import com.uptech.repositorysample.main.di.core.CoreComponent
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

@AuthenticatedScope
@Component(
  dependencies = [
    CoreComponent::class,
    DataSourceComponent::class
  ],
  modules = [
    AuthenticatedModule::class
  ]
)
interface AuthenticatedComponent {
  val authenticatedJob: Job
  val authenticatedScope: CoroutineScope
  val itemRepository: ItemRepository
  val balanceRepository: BalanceRepository
  val logoutInteractor: LogoutInteractor

  @Component.Builder
  interface Builder {
    fun authenticatedComponentHolder(@BindsInstance authenticatedComponentHolder: AuthenticatedComponentHolder): Builder
    fun coreComponent(component: CoreComponent): Builder
    fun datasourceComponent(component: DataSourceComponent): Builder
    fun build(): AuthenticatedComponent
  }
}