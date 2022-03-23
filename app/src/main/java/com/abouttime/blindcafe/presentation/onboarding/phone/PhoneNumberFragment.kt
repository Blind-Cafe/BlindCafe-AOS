package com.abouttime.blindcafe.presentation.onboarding.phone

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class PhoneNumberFragment: BaseFragment<PhoneNumberViewModel>(R.layout.fragment_phone_number) {
    override val viewModel: PhoneNumberViewModel by viewModel()
}