package com.abouttime.blindcafe.presentation.main.home.exit

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentExitBinding
import com.abouttime.blindcafe.presentation.common.confirm.ConfirmDialogFragmentArgs
import org.koin.android.viewmodel.ext.android.viewModel

class ExitFragment: BaseFragment<ExitViewModel>(R.layout.fragment_exit) {
    override val viewModel: ExitViewModel by viewModel()
    private var binding: FragmentExitBinding? = null
    val args: ExitFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentExitBinding = FragmentExitBinding.bind(view)
        binding = fragmentExitBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel


        initTitleTextView(fragmentExitBinding)

    }

    private fun initTitleTextView(fragmentExitBinding: FragmentExitBinding) = with(fragmentExitBinding) {
        if (args.isReport) {
            tvTitleReport.isGone = false
            tvTitleReported.isGone = true
            tvTitleReport.text = args.title
        } else {
            tvTitleReport.isGone = true
            tvTitleReported.isGone = false
            tvTitleReported.text = args.title // TODO Text Decoration 필요!!
        }

    }


}