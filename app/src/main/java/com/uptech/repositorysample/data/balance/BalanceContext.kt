package com.uptech.repositorysample.data.balance

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

class BalanceContext(
  balanceRepository: BalanceRepository,
  authenticatedScope: CoroutineScope
) {

  private val balance: Flow<Long> by lazy {
    balanceRepository.observeBalance()
      .shareIn(
        scope = authenticatedScope,
        started = SharingStarted.WhileSubscribed(2000),
        replay = 1
      )
  }
  fun observeBalance() = balance
}