package com.abouttime.blindcafe.presentation.profile_exchange.open

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ExchangeOpenFragment: BaseFragment<ExchangeOpenViewModel>(R.layout.fragment_exchange_open) {
    override val viewModel: ExchangeOpenViewModel by viewModel()
}