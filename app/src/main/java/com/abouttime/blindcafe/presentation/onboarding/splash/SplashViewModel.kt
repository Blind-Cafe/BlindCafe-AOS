package com.abouttime.blindcafe.presentation.onboarding.splash

import android.util.Log
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
        } else if (infoInput == null) {
            Log.e(INFO_INPUT, "$infoInput")
            moveToAgreementFragment()
        } else {
            moveToMainFragment()
        }
    }



    private fun moveToRuleFragment() {
        moveToDirections(SplashFragmentDirections.actionSplashFragmentToRuleFragment())
    }
    private fun moveToAgreementFragment() {
        moveToDirections(SplashFragmentDirections.actionSplashFragmentToAgreementFragment())
    }
    private fun moveToMainFragment() {
        moveToDirections(SplashFragmentDirections.actionSplashFragmentToMainFragment())
    }
}