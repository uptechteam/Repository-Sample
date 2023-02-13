package com.uptech.repositorysample.data.balance

import com.uptech.repositorysample.data.Cache.Data
import com.uptech.repositorysample.data.Cache.Expired
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class BalanceRepository(
  private val api: BalanceApi,
  private val balanceCache: BalanceCache,
  authenticatedScope: CoroutineScope
) {

  private val balance = balanceCache.observeBalance()
    .onEach { cache ->
      if (cache is Expired) {
        balanceCache.writeBalance(api.getBalance())
      }
    }.filterIsInstance<Data<Long>>()
    .map { cache -> cache.value }
    .stateIn(authenticatedScope, SharingStarted.WhileSubscribed(2000), 0L)

  fun observeBalance(): Flow<Long> = balance

  fun invalidateCache() {
    balanceCache.invalidate()
  }
}
