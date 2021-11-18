package com.abouttime.blindcafe.presentation.profile_exchange.dismiss.confirm

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseDialogFragment
import com.abouttime.blindcafe.databinding.DialogFragmentDismissBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DismissConfirmDialogFragment: BaseDialogFragment<DismissConfirmViewModel>(R.layout.dialog_fragment_dismiss) {
    override val viewModel: DismissConfirmViewModel by viewModel()
    private var binding: DialogFragmentDismissBinding? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialogFragmentDismissBinding = DialogFragmentDismissBinding.bind(view)
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
    }
}