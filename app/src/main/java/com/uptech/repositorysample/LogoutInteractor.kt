package com.uptech.repositorysample

import com.uptech.repositorysample.data.UserManager
import com.uptech.repositorysample.data.balance.BalanceRepository
import com.uptech.repositorysample.data.items.ItemRepository
import com.uptech.repositorysample.main.di.authenticated.AuthenticatedComponentHolder
import kotlinx.coroutines.cancelAndJoin

class LogoutInteractor(
  private val userManager: UserManager,
  private val itemRepository: ItemRepository,
  private val balanceRepository: BalanceRepository,
  private val authenticatedComponentHolder: AuthenticatedComponentHolder
) {

  suspend operator fun invoke() {
    authenticatedComponentHolder.authenticatedComponent?.authenticatedJob?.cancelAndJoin()
    itemRepository.invalidateCache()
    balanceRepository.invalidateCache()
    userManager.logout()
    authenticatedComponentHolder.destroyAuthenticatedComponent()
  }
}