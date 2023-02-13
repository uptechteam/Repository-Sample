package com.uptech.repositorysample.main.di.core

import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher

@Component(
  modules = [CoreModule::class]
)
interface CoreComponent {
  val dispatcher: CoroutineDispatcher

  @Component.Builder
  interface Builder {
    fun build(): CoreComponent
  }
}