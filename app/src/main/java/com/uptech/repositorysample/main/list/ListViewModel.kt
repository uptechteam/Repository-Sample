package com.uptech.repositorysample.main.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uptech.repositorysample.data.balance.BalanceContext
import com.uptech.repositorysample.data.items.ItemContext
import com.uptech.repositorysample.entity.Item
import com.uptech.repositorysample.main.list.ListViewModel.NavigationEvent.BalanceFetchingError
import com.uptech.repositorysample.main.list.ListViewModel.NavigationEvent.ItemFetchingError
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
  private val authenticatedScope: CoroutineScope,
  private val balanceContext: BalanceContext,
  private val itemContext: ItemContext,
  private val events: Channel<NavigationEvent>
) : ViewModel() {
  private var balanceJob: Job? = null
    set(value) {
      field?.cancel()
      field = value
    }
  val balance: MutableStateFlow<Long> = MutableStateFlow(0)
  val items: MutableStateFlow<List<Item>> = MutableStateFlow(emptyList())


  init {
    observeBalance()
    observeItems()
  }

  fun observeBalance() {
    authenticatedScope.launch(
      CoroutineExceptionHandler { _, _ -> events.trySend(BalanceFetchingError) }
    ) {
      balanceJob = balanceContext.observeBalance()
        .onEach { amount -> balance.update { amount } }
        .launchIn(this)
    }
  }

  fun observeItems() {
    authenticatedScope.launch(
      CoroutineExceptionHandler { _, _ -> events.trySend(ItemFetchingError) }
    ) {
      itemContext.observeItems()
        .onEach { items -> this@ListViewModel.items.update { items } }
        .launchIn(this)
    }
  }

  class Factory(
    private val authenticatedScope: CoroutineScope,
    private val balanceContext: BalanceContext,
    private val itemContext: ItemContext,
    private val events: Channel<NavigationEvent>
  ) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
      ListViewModel(
        authenticatedScope = authenticatedScope,
        balanceContext = balanceContext,
        itemContext = itemContext,
        events = events
      ) as T
  }


  sealed interface NavigationEvent {
    object BalanceFetchingError : NavigationEvent
    object ItemFetchingError : NavigationEvent
  }
}