package com.uptech.repositorysample.data.items

import com.uptech.repositorysample.entity.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.shareIn

class ItemDataBridge(
  itemRepository: ItemRepository,
  authenticatedScope: CoroutineScope
) {

  private val items: Flow<List<Item>> by lazy {
    itemRepository.observeItems()
      .shareIn(
        scope = authenticatedScope,
        started = WhileSubscribed(2000),
        replay = 1
      )
  }

  fun observeItems(): Flow<List<Item>> = items
}
