package com.abouttime.blindcafe.presentation.profile_exchange.complete

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentProfileExchagngeCompleteBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileExchangeCompleteFragment: BaseFragment<ProfileExchangeCompleteViewModel>(R.layout.fragment_profile_exchagnge_complete){
    override val viewModel: ProfileExchangeCompleteViewModel by viewModel()
    private var binding: FragmentProfileExchagngeCompleteBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentProfileExchangeCompleteBinding = FragmentProfileExchagngeCompleteBinding.bind(view)
        binding = fragmentProfileExchangeCompleteBinding


    }
}