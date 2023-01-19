package com.uptech.repositorysample.data.balance

import com.uptech.repositorysample.data.Cache
import com.uptech.repositorysample.data.Cache.Data
import com.uptech.repositorysample.data.Cache.Expired
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * sequence diagram
 */
class BalanceCache() {
  private val balance: MutableStateFlow<Cache<Long>> = MutableStateFlow(Expired)

  fun observeBalance(): Flow<Cache<Long>> = balance

  fun writeBalance(balance: Long) {
    this.balance.update { Data(balance) }
  }

  fun invalidate() {
    this.balance.update { Expired }
  }
}
