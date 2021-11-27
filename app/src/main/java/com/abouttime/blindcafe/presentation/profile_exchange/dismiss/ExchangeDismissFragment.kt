package com.abouttime.blindcafe.presentation.profile_exchange.dismiss

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentExchangeDismissBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ExchangeDismissFragment: BaseFragment<ExchangeDismissViewModel>(R.layout.fragment_exchange_dismiss) {
    override val viewModel: ExchangeDismissViewModel by viewModel()
    private var binding: FragmentExchangeDismissBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentDismissBinding = FragmentExchangeDismissBinding.bind(view)
        binding = fragmentDismissBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

    }

}