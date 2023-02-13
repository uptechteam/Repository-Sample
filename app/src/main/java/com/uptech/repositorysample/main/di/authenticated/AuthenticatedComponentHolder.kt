package com.uptech.repositorysample.main.di.authenticated

import com.uptech.repositorysample.data.di.DataSourceComponent
import com.uptech.repositorysample.main.di.core.CoreComponent

class AuthenticatedComponentHolder(
  private val coreComponent: CoreComponent,
  private val dataSourceComponent: DataSourceComponent
) {
  var authenticatedComponent: AuthenticatedComponent? = null

  fun initAuthenticatedComponent() {
    authenticatedComponent = DaggerAuthenticatedComponent.builder()
      .authenticatedComponentHolder(this)
      .coreComponent(coreComponent)
      .datasourceComponent(dataSourceComponent)
      .build()
  }

  fun destroyAuthenticatedComponent() {
    authenticatedComponent = null
  }
}