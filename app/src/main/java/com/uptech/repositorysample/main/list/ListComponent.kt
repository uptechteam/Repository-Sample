package com.uptech.repositorysample.main.list

import com.uptech.repositorysample.FragmentScope
import com.uptech.repositorysample.data.balance.BalanceDataBridge
import com.uptech.repositorysample.data.items.ItemDataBridge
import com.uptech.repositorysample.main.di.authenticated.AuthenticatedComponent
import com.uptech.repositorysample.main.list.ListViewModel.NavigationEvent
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

@FragmentScope
@Component(
  dependencies = [
    AuthenticatedComponent::class
  ],
  modules = [ListModule::class]
)
interface ListComponent {
  val events: Flow<NavigationEvent>

  fun inject(listFragment: ListFragment)

  @Component.Builder
  interface Builder {
    fun authenticatedComponent(component: AuthenticatedComponent): Builder
    fun build(): ListComponent
  }
}

@Module
object ListModule {
  @FragmentScope
  @Provides
  fun provideEvents(): Channel<NavigationEvent> = Channel()

  @FragmentScope
  @Provides
  fun provideEventsFlow(events: Channel<NavigationEvent>) = events.receiveAsFlow()

  @FragmentScope
  @Provides
  fun provideFactory(
    authenticatedScope: CoroutineScope,
    balanceDataBridge: BalanceDataBridge,
    itemDataBridge: ItemDataBridge,
    events: Channel<NavigationEvent>
  ): ListViewModel.Factory =
    ListViewModel.Factory(
      authenticatedScope = authenticatedScope,
      balanceDataBridge = balanceDataBridge,
      itemDataBridge = itemDataBridge,
      events = events
    )
}