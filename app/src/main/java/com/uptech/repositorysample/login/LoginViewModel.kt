package com.uptech.repositorysample.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uptech.repositorysample.login.LoginViewModel.Event.ShowList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class LoginViewModel(
  private val events: Channel<Event>,
  private val loginInteractor: LoginInteractor
) : ViewModel() {

  fun login() {
    viewModelScope.launch {
      loginInteractor.invoke()
      events.send(ShowList)
    }
  }

  sealed interface Event {
    object ShowList : Event
  }

  class Factory(
    private val events: Channel<Event>,
    private val loginInteractor: LoginInteractor
    ) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
      LoginViewModel(
        events,
        loginInteractor
      ) as T
  }
}