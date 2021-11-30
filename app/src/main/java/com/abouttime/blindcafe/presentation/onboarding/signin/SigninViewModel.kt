package com.abouttime.blindcafe.presentation.onboarding.signin

import com.abouttime.blindcafe.common.base.BaseViewModel

class SigninViewModel: BaseViewModel() {

    fun onClickBackButton() {
        popDirections()
    }
    fun onClickNextButton() {
        moveToMainFragment()
    }
    private fun moveToMainFragment() {
        moveToDirections(SigninFragmentDirections.actionSigninFragmentToMainFragment())
    }
}