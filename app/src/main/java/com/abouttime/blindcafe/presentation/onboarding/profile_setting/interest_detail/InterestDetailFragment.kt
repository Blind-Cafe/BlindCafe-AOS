package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class InterestDetailFragment: BaseFragment<InterestDetailViewModel>(R.layout.fragment_interest_detail) {
    override val viewModel: InterestDetailViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}