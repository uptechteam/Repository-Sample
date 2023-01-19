package com.uptech.repositorysample.data.balance

import com.uptech.repositorysample.data.UserManager
import com.uptech.repositorysample.entity.Item
import kotlinx.coroutines.flow.first

class BalanceApi(
  private val userManager: UserManager
) {
  private var mockedBalance: Long = 10000

  suspend fun getBalance(): Long =
    if (userManager.userToken.first() === null) {
      throw IllegalStateException("Unauthorized request")
    } else {
      mockedBalance
    }

  fun buy(item: Item) {
    mockedBalance -= item.cost
  }
}
