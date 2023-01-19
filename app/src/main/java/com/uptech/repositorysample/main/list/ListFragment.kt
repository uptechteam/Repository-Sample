package com.uptech.repositorysample.main.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.uptech.repositorysample.App
import com.uptech.repositorysample.R
import com.uptech.repositorysample.databinding.FragmentListBinding
import com.uptech.repositorysample.main.details.DetailsFragment.Companion.ITEM_ID
import com.uptech.repositorysample.main.di.MainComponentHolder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class ListFragment : Fragment() {

  @Inject lateinit var factory: ListViewModel.Factory
  private val viewModel: ListViewModel by viewModels { factory }
  private var _binding: FragmentListBinding? = null
  private val binding: FragmentListBinding
    get() = _binding!!
  private val itemsAdapter: ItemsAdapter by lazy(NONE) {
    ItemsAdapter { itemId ->
      findNavController().navigate(R.id.detailsFragment, bundleOf(ITEM_ID to itemId))
    }
  }

  override fun onAttach(context: Context) {
    (requireActivity().application as App).let { app ->
      DaggerListComponent.builder()
        .repositoryComponent(app.repositoryComponent)
        .authenticatedComponent(app.applicationComponent.authenticatedComponentHolder.authenticatedComponent!!)
        .build()
        .inject(this)
    }
    super.onAttach(context)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = FragmentListBinding.inflate(inflater, container, false)
    .also { _binding = it }
    .root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel.balance.onEach { binding.balance.text = "balance $it" }.launchIn(lifecycleScope)
    viewModel.items.onEach { items -> itemsAdapter.items = items }.launchIn(lifecycleScope)
    binding.itemsList.adapter = itemsAdapter
  }
}