package com.uptech.repositorysample.data.items

import com.uptech.repositorysample.entity.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn

class ItemContext(
  itemRepository: ItemRepository,
  authenticatedScope: CoroutineScope
) {

  private val items: Flow<List<Item>> by lazy {
    itemRepository.observeItems()
      .stateIn(authenticatedScope, WhileSubscribed(2000), emptyList())
  }

  fun observeItems(): Flow<List<Item>> = items
}
