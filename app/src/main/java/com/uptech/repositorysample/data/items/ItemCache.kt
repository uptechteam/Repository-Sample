package com.uptech.repositorysample.data.items

import com.uptech.repositorysample.data.Cache
import com.uptech.repositorysample.data.Cache.Data
import com.uptech.repositorysample.data.Cache.Expired
import com.uptech.repositorysample.entity.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class ItemCache {
  private val items: MutableStateFlow<Cache<List<Item>>> = MutableStateFlow(Expired)

  fun observeItems(): Flow<Cache<List<Item>>> = items

  fun writeItems(items: List<Item>) {
    this.items.update { Data(items) }
  }

  fun getItem(itemId: String): Cache<Item> =
    (items.value as? Data)?.value
      ?.firstOrNull { it.id == itemId }
      ?.let { Data(it) } ?: Expired

  fun invalidate() {
    items.update { Expired }
  }
}