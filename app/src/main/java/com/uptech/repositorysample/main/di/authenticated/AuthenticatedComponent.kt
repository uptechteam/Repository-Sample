package com.uptech.repositorysample.main.di.authenticated

import com.uptech.repositorysample.AuthenticatedScope
import com.uptech.repositorysample.data.RepositoryComponent
import com.uptech.repositorysample.data.balance.BalanceDataBridge
import com.uptech.repositorysample.data.di.DataSourceComponent
import com.uptech.repositorysample.data.items.ItemDataBridge
import com.uptech.repositorysample.main.di.core.CoreComponent
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

@AuthenticatedScope
@Component(
  dependencies = [
    CoreComponent::class,
    DataSourceComponent::class,
    RepositoryComponent::class
  ],
  modules = [AuthenticatedModule::class]
)
interface AuthenticatedComponent {
  val authenticatedJob: Job
  val authenticatedScope: CoroutineScope
  val itemDataBridge: ItemDataBridge
  val balanceDataBridge: BalanceDataBridge

  @Component.Builder
  interface Builder {
    fun authenticatedComponentHolder(@BindsInstance authenticatedComponentHolder: AuthenticatedComponentHolder): Builder
    fun coreComponent(component: CoreComponent): Builder
    fun datasourceComponent(component: DataSourceComponent): Builder
    fun repositoryComponent(component: RepositoryComponent): Builder
    fun build(): AuthenticatedComponent
  }
}