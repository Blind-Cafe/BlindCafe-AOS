package com.abouttime.blindcafe.presentation.profile_exchange.accept

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentExchangeAcceptBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ExchangeAcceptFragment: BaseFragment<ExchangeAcceptViewModel>(R.layout.fragment_exchange_accept) {
    override val viewModel: ExchangeAcceptViewModel by viewModel()
    private var binding: FragmentExchangeAcceptBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentExchangeAcceptBinding = FragmentExchangeAcceptBinding.bind(view)
        binding = fragmentExchangeAcceptBinding

    }
}