package com.abouttime.blindcafe.presentation.profile_exchange.wait

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentExchangeWaitBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ExchangeWaitFragment: BaseFragment<ExchangeWaitViewModel>(R.layout.fragment_exchange_wait) {
    override val viewModel: ExchangeWaitViewModel by viewModel()
    private var binding: FragmentExchangeWaitBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentExchangeWaitBinding = FragmentExchangeWaitBinding.bind(view)
        binding = fragmentExchangeWaitBinding

    }
}