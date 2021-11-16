package com.abouttime.blindcafe.presentation.chat.quit.reason

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseDialogFragment
import com.abouttime.blindcafe.databinding.DialogFragmentQuitReasonBinding
import org.koin.android.viewmodel.ext.android.viewModel

class QuitReasonDialogFragment:BaseDialogFragment<QuitReasonViewModel>(R.layout.dialog_fragment_quit_reason) {
    override val viewModel: QuitReasonViewModel by viewModel()
    private var binding: DialogFragmentQuitReasonBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialogFragmentQuitReasonBinding = DialogFragmentQuitReasonBinding.bind(view)
        binding = dialogFragmentQuitReasonBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
    }
}