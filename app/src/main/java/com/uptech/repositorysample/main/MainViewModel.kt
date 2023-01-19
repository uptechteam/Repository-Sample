package com.uptech.repositorysample.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uptech.repositorysample.LogoutInteractor
import com.uptech.repositorysample.data.UserManager
import com.uptech.repositorysample.main.MainViewModel.Event.Login
import com.uptech.repositorysample.main.MainViewModel.Event.ShowList
import com.uptech.repositorysample.main.di.authenticated.AuthenticatedComponentHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
  private val userManager: UserManager,
  private val events: Channel<Event>,
  private val doLogout: LogoutInteractor,
  private val authenticatedComponentHolder: AuthenticatedComponentHolder,
) : ViewModel() {

  init {
    viewModelScope.launch(Dispatchers.IO) {
      userManager.userToken
        .onEach { token ->
          if (token === null) {
            events.send(Login)
          } else {
            authenticatedComponentHolder.initAuthenticatedComponent()
            events.send(ShowList)
          }
        }.launchIn(this)
    }
  }

  fun logout() {
    viewModelScope.launch(Dispatchers.IO) {
      doLogout()
    }
  }

  sealed interface Event {
    object Login : Event
    object ShowList :Event
  }

  class Factory(
    private val userManager: UserManager,
    private val events: Channel<Event>,
    private val logoutInteractor: LogoutInteractor,
    private val authenticatedComponentHolder: AuthenticatedComponentHolder
  ) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
      MainViewModel(
        userManager = userManager,
        events = events,
        doLogout = logoutInteractor,
        authenticatedComponentHolder = authenticatedComponentHolder
      ) as T
  }
}