package com.uptech.repositorysample.main.di.authenticated

import com.uptech.repositorysample.main.di.core.CoreComponent

class AuthenticatedComponentHolder(
  private val coreComponent: CoreComponent
) {
  var authenticatedComponent: AuthenticatedComponent? = null

  fun initAuthenticatedComponent() {
    authenticatedComponent = DaggerAuthenticatedComponent.builder()
      .coreComponent(coreComponent)
      .build()
  }

  fun destroyAuthenticatedComponent() {
    authenticatedComponent = null
  }
}