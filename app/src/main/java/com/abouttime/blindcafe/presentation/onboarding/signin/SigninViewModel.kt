package com.abouttime.blindcafe.presentation.onboarding.signin

import com.abouttime.blindcafe.common.base.BaseViewModel

class SigninViewModel: BaseViewModel() {

    fun onClickNextButton() {
        moveToMainFragment()
    }
    private fun moveToMainFragment() {
        moveToDirections(SigninFragmentDirections.actionSigninFragmentToMainFragment())
    }
}