package com.uptech.repositorysample.main.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uptech.repositorysample.data.items.ItemRepository
import com.uptech.repositorysample.entity.Item
import com.uptech.repositorysample.main.details.DetailsViewModel.Event.Close
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
  private val itemId: String,
  private val itemRepository: ItemRepository,
  private val authenticatedScope: CoroutineScope
) : ViewModel() {

  private val _item: MutableStateFlow<Item?> = MutableStateFlow(null)
  val item: Flow<Item> = _item.filterNotNull()

  private val _events: Channel<Event> = Channel()
  val events: Flow<Event> = _events.receiveAsFlow()

  init {
    authenticatedScope.launch {
      itemRepository.observeItems().map {
        it.first { it.id == itemId }
      }.onEach {
        _item.value = it//itemRepository.getItem(itemId)
      }.launchIn(this)
    }
  }

  fun buy() {
    _item.value?.let(itemRepository::buy)
    _events.trySend(Close)
  }

  sealed interface Event {
    object Close : Event
  }

  class Factory constructor(
    private val itemId: String,
    private val itemRepository: ItemRepository,
    private val authenticatedScope: CoroutineScope
  ) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
      DetailsViewModel(itemId, itemRepository, authenticatedScope) as T
  }
}