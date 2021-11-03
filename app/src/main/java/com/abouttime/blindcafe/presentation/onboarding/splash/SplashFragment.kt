package com.abouttime.blindcafe.presentation.onboarding.splash

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment: BaseFragment<SplashViewModel>(R.layout.fragment_splash) {
    override val viewModel: SplashViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO 여기서 jwt 여부 확인해서 main 화면으로 갈지, 온보딩 화면으로 갈지 결정
        moveToRuleFragment()
    }

    private fun moveToRuleFragment() {
        moveToDirections(SplashFragmentDirections.actionSplashFragmentToRuleFragment())
    }


}