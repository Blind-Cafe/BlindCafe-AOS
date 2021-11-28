package com.abouttime.blindcafe.presentation.profile_exchange.wait

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentExchangeWaitBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ExchangeWaitFragment: BaseFragment<ExchangeWaitViewModel>(R.layout.fragment_exchange_wait) {
    override val viewModel: ExchangeWaitViewModel by viewModel()
    private var binding: FragmentExchangeWaitBinding? = null
    private val args: ExchangeWaitFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentExchangeWaitBinding = FragmentExchangeWaitBinding.bind(view)
        binding = fragmentExchangeWaitBinding
        initNavArgs()

    }
    private fun initNavArgs() {
        val nick = args.partnerNickname
        val reason = args.reason
        initTitleTextView(nick, reason)
    }
    private fun initTitleTextView(nickname: String, reason: String) {
        binding?.tvTitle?.text = getString(R.string.profile_exchange_wait).format(nickname, reason)
    }
}