package com.uptech.repositorysample.main.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.uptech.repositorysample.databinding.DialogErrorBinding
import com.uptech.repositorysample.main.list.ErrorDialogHost

class ErrorDialog : DialogFragment() {

  private var _binding: DialogErrorBinding? = null
  private val binding: DialogErrorBinding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View = DialogErrorBinding.inflate(inflater, container, false)
    .also { _binding = it }
    .root

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.retry.setOnClickListener {
      (parentFragment as? ErrorDialogHost)?.onRetry(arguments)
    }
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }

  companion object {
    const val SOURCE = "source"
  }
}