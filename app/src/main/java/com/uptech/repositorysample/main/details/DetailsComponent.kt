package com.uptech.repositorysample.main.details

import com.uptech.repositorysample.FragmentScope
import com.uptech.repositorysample.data.RepositoryComponent
import com.uptech.repositorysample.data.items.ItemRepository
import com.uptech.repositorysample.main.details.DetailsComponent.Companion.ITEM_ID
import com.uptech.repositorysample.main.di.authenticated.AuthenticatedComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named

@FragmentScope
@Component(
  dependencies = [
    RepositoryComponent::class,
    AuthenticatedComponent::class
  ],
  modules = [
    DetailsModule::class
  ]
)
interface DetailsComponent {
  fun inject(fragment: DetailsFragment)

  @Component.Builder
  interface Builder {
    fun itemId(@BindsInstance @Named(ITEM_ID) itemId: String): Builder
    fun repositoryComponent(component: RepositoryComponent): Builder
    fun authenticatedComponent(component: AuthenticatedComponent): Builder
    fun build(): DetailsComponent
  }

  companion object {
    const val ITEM_ID = "item_id"
  }
}

@Module
object DetailsModule {
  @FragmentScope
  @Provides
  fun provideFactory(
    @Named(ITEM_ID) itemId: String,
    itemRepository: ItemRepository,
    authenticatedScope: CoroutineScope
  ): DetailsViewModel.Factory =
    DetailsViewModel.Factory(
      itemId,
      itemRepository,
      authenticatedScope
    )
}