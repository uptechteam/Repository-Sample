package com.uptech.repositorysample.data.balance

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class BalanceContext(
  balanceRepository: BalanceRepository,
  authenticatedScope: CoroutineScope
) {

  private val balance: Flow<Long> by lazy {
    balanceRepository.observeBalance()
      .stateIn(authenticatedScope, SharingStarted.WhileSubscribed(2000), 0L)
  }
  fun observeBalance() = balance
}