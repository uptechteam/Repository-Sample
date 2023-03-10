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
import com.uptech.repositorysample.main.dialog.ErrorDialog.Companion.SOURCE
import com.uptech.repositorysample.main.list.ErrorDialogHost.Companion.BALANCE
import com.uptech.repositorysample.main.list.ErrorDialogHost.Companion.ITEMS
import com.uptech.repositorysample.main.list.ListViewModel.NavigationEvent
import com.uptech.repositorysample.main.list.ListViewModel.NavigationEvent.BalanceFetchingError
import com.uptech.repositorysample.main.list.ListViewModel.NavigationEvent.ItemFetchingError
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class ListFragment : Fragment(), ErrorDialogHost {

  @Inject lateinit var factory: ListViewModel.Factory
  private lateinit var component: ListComponent
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
        .also { component = it }
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
    component.events.onEach(::handleEvent).launchIn(lifecycleScope)
    viewModel.balance.onEach { binding.balance.text = "balance $it" }.launchIn(lifecycleScope)
    viewModel.items.onEach { items -> itemsAdapter.items = items }.launchIn(lifecycleScope)
    binding.itemsList.adapter = itemsAdapter
  }

  private fun handleEvent(event: NavigationEvent) {
    when (event) {
      BalanceFetchingError -> showErrorDialog(BALANCE)
      ItemFetchingError -> showErrorDialog(ITEMS)
    }
  }

  private fun showErrorDialog(source: String) {
    findNavController().navigate(
      R.id.errorDialog,
      bundleOf(SOURCE to source)
    )
  }

  override fun onRetry(args: Bundle?) {
    when(args?.getString(SOURCE)) {
      BALANCE -> viewModel.observeBalance()
      ITEMS -> viewModel.observeItems()
    }
  }
}

interface ErrorDialogHost {
  fun onRetry(args: Bundle?)

  companion object {
    const val BALANCE = "balance"
    const val ITEMS = "items"
  }
}