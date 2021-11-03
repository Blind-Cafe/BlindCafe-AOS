package com.abouttime.blindcafe.presentation.main.matching

import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.R
import org.koin.android.viewmodel.ext.android.viewModel

class MatchingFragment: BaseFragment<MatchingViewModel>(R.layout.fragment_matching) {
    override val viewModel: MatchingViewModel by viewModel()
}