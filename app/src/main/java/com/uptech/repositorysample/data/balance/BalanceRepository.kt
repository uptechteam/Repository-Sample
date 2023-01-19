package com.uptech.repositorysample.data.balance

import com.uptech.repositorysample.data.Cache.Data
import com.uptech.repositorysample.data.Cache.Expired
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.filter
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
      }.filter { cache -> cache is Data }
      .map { cache -> (cache as Data<Long>).value }

  fun invalidateCache() {
    balanceCache.invalidate()
  }
}
