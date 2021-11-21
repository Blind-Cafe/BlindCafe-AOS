package com.abouttime.blindcafe.presentation.common.confirm

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseDialogFragment
import com.abouttime.blindcafe.databinding.DialogFragmentConfirmBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ConfirmDialogFragment: BaseDialogFragment<ConfirmViewModel>(R.layout.dialog_fragment_confirm) {
    override val viewModel: ConfirmViewModel by viewModel()
    private var binding: DialogFragmentConfirmBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialogFragmentConfirmBinding = DialogFragmentConfirmBinding.bind(view)
        binding = dialogFragmentConfirmBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
    }
}