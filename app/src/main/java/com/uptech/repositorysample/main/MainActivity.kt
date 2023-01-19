package com.uptech.repositorysample.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.uptech.repositorysample.App
import com.uptech.repositorysample.R
import com.uptech.repositorysample.databinding.ActivityLoginBinding
import com.uptech.repositorysample.databinding.ActivityMainBinding
import com.uptech.repositorysample.login.LoginActivity
import com.uptech.repositorysample.main.MainViewModel.Event
import com.uptech.repositorysample.main.MainViewModel.Event.Login
import com.uptech.repositorysample.main.MainViewModel.Event.ShowList
import com.uptech.repositorysample.main.di.DaggerMainComponent
import com.uptech.repositorysample.main.di.MainComponent
import com.uptech.repositorysample.main.di.MainComponentHolder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainActivity : AppCompatActivity(),
  MainComponentHolder {

  @Inject lateinit var viewModelFactory: MainViewModel.Factory

  private lateinit var binding: ActivityMainBinding

  private lateinit var _mainComponent: MainComponent
  override val mainComponent: MainComponent
    get() = _mainComponent

  private val viewModel: MainViewModel by viewModels { viewModelFactory }
  override fun onCreate(savedInstanceState: Bundle?) {
    (application as App).let { app ->
      DaggerMainComponent.builder()
        .dataSourceComponent(app.dataSourceComponent)
        .repositoryComponent(app.repositoryComponent)
        .applicationComponent(app.applicationComponent)
      .build()
      .also { component ->
        _mainComponent = component
        component.events
          .onEach(::handleEvent)
          .launchIn(lifecycleScope)
      }.inject(this)
    }
    viewModel
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
      .also { binding -> setContentView(binding.root) }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.logout -> viewModel.logout()
    }
    return true
  }

  private fun handleEvent(event: Event) {
    when (event) {
      Login -> startActivity(
        Intent(this, LoginActivity::class.java).apply {
          flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
      )
      ShowList -> {
        Navigation.findNavController(this, R.id.nav_host_fragment).setGraph(R.navigation.nav_graph)
      }
    }
  }
}