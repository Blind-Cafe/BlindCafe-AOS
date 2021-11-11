package com.abouttime.blindcafe.presentation.onboarding.splash

import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY.INFO_INPUT
import com.abouttime.blindcafe.common.constants.Retrofit.JWT

class SplashViewModel: BaseViewModel() {

    init {
        checkJwt()
    }


    private fun checkJwt() {
        val jwt = getStringData(JWT)
        val infoInput = getStringData(INFO_INPUT)
        if (jwt.isNullOrEmpty()) {
            moveToRuleFragment()
        } else if (infoInput.isNullOrEmpty()) {
            moveToAgreementFragment()
        } else {
            moveToMainFragment()
        }
    }



    fun moveToRuleFragment() {
        moveToDirections(SplashFragmentDirections.actionSplashFragmentToRuleFragment())
    }
    fun moveToAgreementFragment() {
        moveToDirections(SplashFragmentDirections.actionSplashFragmentToAgreementFragment())
    }
    fun moveToMainFragment() {
        moveToDirections(SplashFragmentDirections.actionSplashFragmentToMainFragment())
    }
}