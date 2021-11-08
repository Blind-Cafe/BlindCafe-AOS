package com.abouttime.blindcafe.presentation.onboarding.splash

import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.Retrofit.JWT

class SplashViewModel: BaseViewModel() {

    init {
        checkJwt()
    }


    private fun checkJwt() {
        val jwt = getStringData(JWT)
        if (jwt == null) {
            moveToRuleFragment()
        } else {
            moveToMainFragment()
        }
    }



    fun moveToRuleFragment() {
        moveToDirections(SplashFragmentDirections.actionSplashFragmentToRuleFragment())
    }
    fun moveToMainFragment() {
        moveToDirections(SplashFragmentDirections.actionSplashFragmentToMainFragment())
    }
}