package com.abouttime.blindcafe.presentation.splash

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment: BaseFragment<SplashViewModel>(R.layout.fragment_splash) {
    override val viewModel: SplashViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }






}