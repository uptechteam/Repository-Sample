package com.uptech.repositorysample.main.list

import com.uptech.repositorysample.FragmentScope
import com.uptech.repositorysample.data.balance.BalanceRepository
import com.uptech.repositorysample.data.di.RepositoryComponent
import com.uptech.repositorysample.data.items.ItemRepository
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
    RepositoryComponent::class,
    AuthenticatedComponent::class
  ],
  modules = [ListModule::class]
)
interface ListComponent {
  val events: Flow<NavigationEvent>

  fun inject(listFragment: ListFragment)

  @Component.Builder
  interface Builder {
    fun repositoryComponent(component: RepositoryComponent): Builder
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
    balanceRepository: BalanceRepository,
    itemRepository: ItemRepository,
    events: Channel<NavigationEvent>
  ): ListViewModel.Factory =
    ListViewModel.Factory(
      authenticatedScope = authenticatedScope,
      balanceRepository = balanceRepository,
      itemRepository = itemRepository,
      events = events
    )
}