package com.uptech.repositorysample.main.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.uptech.repositorysample.App
import com.uptech.repositorysample.databinding.FragmentDetailsBinding
import com.uptech.repositorysample.main.details.DetailsViewModel.Event
import com.uptech.repositorysample.main.details.DetailsViewModel.Event.Close
import com.uptech.repositorysample.main.details.DetailsViewModel.Factory
import com.uptech.repositorysample.main.di.MainComponentHolder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DetailsFragment : Fragment() {
  @Inject lateinit var factory: Factory
  private val viewModel: DetailsViewModel by viewModels { factory }
  private  var _binding: FragmentDetailsBinding? = null
  val binding: FragmentDetailsBinding
    get() = _binding!!

  override fun onAttach(context: Context) {
    (requireActivity().application as App).let { app ->
      DaggerDetailsComponent.builder()
        .itemId(arguments?.getString(ITEM_ID)!!)
        .repositoryComponent(app.repositoryComponent)
        .authenticatedComponent(
          app.applicationComponent.authenticatedComponentHolder.authenticatedComponent!!
        ).build()
        .inject(this)
    }
    super.onAttach(context)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = FragmentDetailsBinding.inflate(inflater, container, false)
    .also {
      _binding = it
      viewModel.events.onEach(::handleEvent).launchIn(lifecycleScope)
    }
    .root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.buy.setOnClickListener { viewModel.buy() }
    viewModel.item
      .onEach { item ->
        with (binding) {
          name.text = item.name
          price.text = "price: ${item.cost}"
          image.setImageResource(item.imageRes)
        }
      }.launchIn(lifecycleScope)
  }

  private fun handleEvent(event: Event) {
    when(event) {
      Close -> findNavController().popBackStack()
    }
  }

  companion object {
    const val ITEM_ID = "item_id"
  }
}