package com.abouttime.blindcafe.presentation.onboarding.splash

import com.abouttime.blindcafe.common.base.BaseViewModel

class SplashViewModel: BaseViewModel() {



    fun moveToRuleFragment() {
        moveToDirections(SplashFragmentDirections.actionSplashFragmentToRuleFragment())
    }
}