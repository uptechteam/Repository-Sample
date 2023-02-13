package com.uptech.repositorysample.data.items

import com.uptech.repositorysample.data.Cache.Data
import com.uptech.repositorysample.data.Cache.Expired
import com.uptech.repositorysample.data.balance.BalanceApi
import com.uptech.repositorysample.data.balance.BalanceCache
import com.uptech.repositorysample.entity.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ItemRepository(
  private val itemApi: ItemApi,
  private val itemCache: ItemCache,
  private val balanceApi: BalanceApi,
  private val balanceCache: BalanceCache
) {
  fun buy(item: Item) {
    mockedBuyLogic(item)
    itemCache.invalidate()
    balanceCache.invalidate()
  }

  private fun mockedBuyLogic(item: Item) {
    balanceApi.buy(item)
    itemApi.buy(item)
  }

  fun observeItems(): Flow<List<Item>> =
    itemCache.observeItems()
      .onEach { cache ->
        if (cache is Expired) {
          itemCache.writeItems(itemApi.getItems())//will cause as many
        // API call as view models currently observing this data
        }
      }.filterIsInstance<Data<List<Item>>>()
      .map { itemCache -> itemCache.value }

  suspend fun getItem(itemId: String): Item =
    (itemCache.getItem(itemId) as? Data)?.value ?: itemCache.writeItems(itemApi.getItems())
      .let { (itemCache.getItem(itemId) as Data).value }

  fun invalidateCache() {
    itemCache.invalidate()
  }
}