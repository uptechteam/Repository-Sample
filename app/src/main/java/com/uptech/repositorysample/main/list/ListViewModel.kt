package com.uptech.repositorysample.main.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uptech.repositorysample.data.balance.BalanceRepository
import com.uptech.repositorysample.data.items.ItemRepository
import com.uptech.repositorysample.entity.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ListViewModel(
  private val authenticatedScope: CoroutineScope,
  private val balanceRepository: BalanceRepository,
  private val itemRepository: ItemRepository
) : ViewModel() {
  val balance: MutableStateFlow<Long> = MutableStateFlow(0)
  val items: MutableStateFlow<List<Item>> = MutableStateFlow(emptyList())

  init {
    balanceRepository.observeBalance()
      .onEach { amount -> balance.update { amount } }
      .launchIn(authenticatedScope)
    itemRepository.observeItems()
      .onEach { items -> this.items.update { items } }
      .launchIn(authenticatedScope)
  }

  class Factory(
    private val authenticatedScope: CoroutineScope,
    private val balanceRepository: BalanceRepository,
    private val itemRepository: ItemRepository
  ) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
      ListViewModel(
        authenticatedScope = authenticatedScope,
        balanceRepository = balanceRepository,
        itemRepository = itemRepository
      ) as T
  }
}