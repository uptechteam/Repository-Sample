package com.uptech.repositorysample.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.uptech.repositorysample.App
import com.uptech.repositorysample.databinding.ActivityLoginBinding
import com.uptech.repositorysample.login.LoginViewModel.Event
import com.uptech.repositorysample.login.LoginViewModel.Event.ShowList
import com.uptech.repositorysample.main.MainActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
  private lateinit var binding: ActivityLoginBinding

  @Inject lateinit var factory: LoginViewModel.Factory
  private val viewModel: LoginViewModel by viewModels { factory }

  override fun onCreate(savedInstanceState: Bundle?) {
    val loginComponent = (application as App).let { app ->
      DaggerLoginComponent.builder()
        .applicationComponent(app.applicationComponent)
        .dataSourceComponent(app.dataSourceComponent)
        .build()
    }.also { component -> component.inject(this) }
    super.onCreate(savedInstanceState)
    loginComponent.events.onEach(::handleEvents).launchIn(lifecycleScope)
    binding = ActivityLoginBinding.inflate(layoutInflater)
      .also { binding -> setContentView(binding.root) }
    binding.login.setOnClickListener {
      viewModel.login()
    }
  }

  private fun handleEvents(event: Event) {
    when (event) {
      ShowList -> startActivity(
        Intent(this, MainActivity::class.java)
          .apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
          }
      )
    }
  }
}