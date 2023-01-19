package com.uptech.repositorysample.main.di.authenticated

import com.uptech.repositorysample.AuthenticatedScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
object AuthenticatedModule {
  val authenticatedJob: Job
    @AuthenticatedScope
    @Provides
    get() = SupervisorJob()

  @AuthenticatedScope
  @Provides
  fun provideAuthenticatedScope(
    dispatcher: CoroutineDispatcher
  ): CoroutineScope = CoroutineScope(authenticatedJob + dispatcher)
}