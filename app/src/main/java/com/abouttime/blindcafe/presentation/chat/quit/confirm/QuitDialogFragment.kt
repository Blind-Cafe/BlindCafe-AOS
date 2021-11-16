package com.abouttime.blindcafe.presentation.chat.quit.confirm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseDialogFragment
import com.abouttime.blindcafe.databinding.DialogFragmentQuitBinding
import org.koin.android.viewmodel.ext.android.viewModel

class QuitDialogFragment: BaseDialogFragment<QuitViewModel>(R.layout.dialog_fragment_quit) {
    override val viewModel: QuitViewModel by viewModel()
    private var binding: DialogFragmentQuitBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialogFragmentQuitBinding = DialogFragmentQuitBinding.bind(view)
        binding = dialogFragmentQuitBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel


    }
}