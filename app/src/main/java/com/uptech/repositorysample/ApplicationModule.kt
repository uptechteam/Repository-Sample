package com.uptech.repositorysample

import com.uptech.repositorysample.data.RepositoryComponent
import com.uptech.repositorysample.data.di.DataSourceComponent
import com.uptech.repositorysample.main.di.authenticated.AuthenticatedComponentHolder
import com.uptech.repositorysample.main.di.core.CoreComponent
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object ApplicationModule {

  @ApplicationScope
  @Provides
  fun provideAuthenticatedComponentHolder(
    coreComponent: CoreComponent,
    dataSourceComponent: DataSourceComponent,
    repositoryComponent: RepositoryComponent
  ): AuthenticatedComponentHolder = AuthenticatedComponentHolder(
    coreComponent = coreComponent,
    dataSourceComponent = dataSourceComponent,
    repositoryComponent = repositoryComponent
  )

  @ApplicationScope
  @Provides
  fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}