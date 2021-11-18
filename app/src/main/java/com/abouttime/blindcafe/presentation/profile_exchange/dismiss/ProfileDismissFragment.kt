package com.abouttime.blindcafe.presentation.profile_exchange.dismiss

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentProfileDismissBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileDismissFragment: BaseFragment<DismissViewModel>(R.layout.fragment_profile_dismiss) {
    override val viewModel: DismissViewModel by viewModel()
    private var binding: FragmentProfileDismissBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentProfileDismissBinding = FragmentProfileDismissBinding.bind(view)
        binding = fragmentProfileDismissBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

    }

}