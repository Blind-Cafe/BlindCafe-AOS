package com.abouttime.blindcafe.presentation.main.home

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment<HomeViewModel>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel by viewModel()
}