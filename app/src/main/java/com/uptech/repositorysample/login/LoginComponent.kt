package com.uptech.repositorysample.login

import com.uptech.repositorysample.ActivityScope
import com.uptech.repositorysample.ApplicationComponent
import com.uptech.repositorysample.data.di.DataSourceComponent
import com.uptech.repositorysample.login.LoginViewModel.Event
import dagger.Component
import kotlinx.coroutines.flow.Flow

@ActivityScope
@Component(
  dependencies = [
    ApplicationComponent::class,
    DataSourceComponent::class
  ],
  modules = [LoginModule::class]
)
interface LoginComponent {
  fun inject(activity: LoginActivity)
  val events: Flow<Event>

  @Component.Builder
  interface Builder {
    fun applicationComponent(component: ApplicationComponent): Builder
    fun dataSourceComponent(component: DataSourceComponent): Builder
    fun build(): LoginComponent
  }
}