package com.uptech.repositorysample.main.di

import com.uptech.repositorysample.ActivityScope
import com.uptech.repositorysample.ApplicationComponent
import com.uptech.repositorysample.LogoutInteractor
import com.uptech.repositorysample.data.RepositoryComponent
import com.uptech.repositorysample.data.di.DataSourceComponent
import com.uptech.repositorysample.main.MainActivity
import com.uptech.repositorysample.main.MainViewModel.Event
import dagger.Component
import kotlinx.coroutines.flow.Flow

@ActivityScope
@Component(
  dependencies = [
    DataSourceComponent::class,
    RepositoryComponent::class,
    ApplicationComponent::class
],
  modules = [MainModule::class]
)
interface MainComponent {
  val events: Flow<Event>
  val logoutInteractor: LogoutInteractor

  fun inject(activity: MainActivity)

  @Component.Builder
  interface Builder {
    fun dataSourceComponent(component: DataSourceComponent): Builder
    fun repositoryComponent(component: RepositoryComponent): Builder
    fun applicationComponent(component: ApplicationComponent): Builder
    fun build(): MainComponent
  }
}