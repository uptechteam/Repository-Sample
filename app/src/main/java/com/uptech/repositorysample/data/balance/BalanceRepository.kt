package com.uptech.repositorysample.data.balance

import com.uptech.repositorysample.data.Cache.Data
import com.uptech.repositorysample.data.Cache.Expired
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class BalanceRepository(
  private val api: BalanceApi,
  private val balanceCache: BalanceCache,
) {

  fun observeBalance(): Flow<Long> =
    balanceCache.observeBalance()
      .onEach { cache ->
        if (cache is Expired) {
          balanceCache.writeBalance(api.getBalance())
        }
      }.filterIsInstance<Data<Long>>()
      .map { cache -> cache.value }

  fun invalidateCache() {
    balanceCache.invalidate()
  }
}
