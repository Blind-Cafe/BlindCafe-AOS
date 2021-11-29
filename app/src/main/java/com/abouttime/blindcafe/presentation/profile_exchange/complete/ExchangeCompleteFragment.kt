package com.abouttime.blindcafe.presentation.profile_exchange.complete

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentExchangeAcceptBinding
import com.abouttime.blindcafe.databinding.FragmentExchangeCompleteBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ExchangeCompleteFragment: BaseFragment<ExchangeCompleteViewModel>(R.layout.fragment_exchange_complete){
    override val viewModel: ExchangeCompleteViewModel by viewModel()
    private var binding: FragmentExchangeCompleteBinding? = null
    private val args: ExchangeCompleteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentExchangeCompleteBinding = FragmentExchangeCompleteBinding.bind(view)
        binding = fragmentExchangeCompleteBinding

        initArgs()
        initBackButton()
    }

    private fun initArgs() {
        viewModel.matchingId = args.matchingId
    }
    private fun initBackButton() {
        binding?.ivBack?.setOnClickListener {
            popOneDirections()
        }
    }
}