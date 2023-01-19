package com.uptech.repositorysample.main.di.authenticated

import com.uptech.repositorysample.AuthenticatedScope
import com.uptech.repositorysample.LogoutInteractor
import com.uptech.repositorysample.data.UserManager
import com.uptech.repositorysample.data.di.DataSourceComponent
import com.uptech.repositorysample.main.di.MainComponent
import com.uptech.repositorysample.main.di.core.CoreComponent
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import javax.inject.Singleton

@AuthenticatedScope
@Component(
  dependencies = [
    CoreComponent::class
  ],
  modules = [AuthenticatedModule::class]
)
interface AuthenticatedComponent {
  val authenticatedJob: Job
  val authenticatedScope: CoroutineScope

  @Component.Builder
  interface Builder {
    fun coreComponent(component: CoreComponent): Builder
    fun build(): AuthenticatedComponent
  }
}