package com.abouttime.blindcafe.presentation.profile_exchange.complete

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentExchangeAcceptBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ExchangeCompleteFragment: BaseFragment<ExchangeCompleteViewModel>(R.layout.fragment_exchange_complete){
    override val viewModel: ExchangeCompleteViewModel by viewModel()
    private var binding: FragmentExchangeAcceptBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentExchangeCompleteBinding = FragmentExchangeAcceptBinding.bind(view)
        binding = fragmentExchangeCompleteBinding


    }
}