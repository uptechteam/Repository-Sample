package com.uptech.repositorysample.login

import com.uptech.repositorysample.data.UserManager
import com.uptech.repositorysample.main.di.authenticated.AuthenticatedComponentHolder

class LoginInteractor(
  private val userManager: UserManager,
  private val authenticatedComponentHolder: AuthenticatedComponentHolder
) {

  suspend operator fun invoke() {
    authenticatedComponentHolder.initAuthenticatedComponent()
    userManager.login()
  }
}