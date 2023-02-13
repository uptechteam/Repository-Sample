package com.uptech.repositorysample

import com.uptech.repositorysample.data.di.DataSourceComponent
import com.uptech.repositorysample.main.di.authenticated.AuthenticatedComponentHolder
import com.uptech.repositorysample.main.di.core.CoreComponent
import dagger.Component

@ApplicationScope
@Component(
  dependencies = [
    CoreComponent::class,
    DataSourceComponent::class,
  ],
  modules = [
    ApplicationModule::class
  ]
)
interface ApplicationComponent {
  val authenticatedComponentHolder: AuthenticatedComponentHolder

  @Component.Builder
  interface Builder {
    fun coreComponent(component: CoreComponent): Builder
    fun dataSourceComponent(component: DataSourceComponent): Builder
    fun build(): ApplicationComponent
  }
}