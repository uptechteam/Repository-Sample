package com.uptech.repositorysample.main.di.core

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object CoreModule {

  val dispatcher: CoroutineDispatcher
    @Provides
    get() = Dispatchers.IO
}