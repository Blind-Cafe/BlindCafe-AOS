package com.abouttime.blindcafe.presentation.profile_exchange.dismiss.confirm

import com.abouttime.blindcafe.common.base.BaseViewModel

class DismissConfirmViewModel: BaseViewModel() {
    fun onClickYesButton() {
        popDirections()
    }
    fun onClickNoButton() {
        popDirections()
    }
}