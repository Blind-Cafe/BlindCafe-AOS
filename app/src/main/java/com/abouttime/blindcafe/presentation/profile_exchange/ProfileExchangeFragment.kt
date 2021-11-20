package com.abouttime.blindcafe.presentation.profile_exchange

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentProfileExchangeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileExchangeFragment: BaseFragment<ProfileExchangeViewModel>(R.layout.fragment_profile_exchange) {
    override val viewModel: ProfileExchangeViewModel by viewModel()
    private var binding: FragmentProfileExchangeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentProfileExchangeBinding = FragmentProfileExchangeBinding.bind(view)
        binding = fragmentProfileExchangeBinding

    }
}