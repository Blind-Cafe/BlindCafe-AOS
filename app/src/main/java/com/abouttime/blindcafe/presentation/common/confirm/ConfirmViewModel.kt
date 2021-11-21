package com.abouttime.blindcafe.presentation.common.confirm

import com.abouttime.blindcafe.common.base.BaseViewModel

class ConfirmViewModel: BaseViewModel() {
    fun onClickYesButton() {
        popDirections()
    }
    fun onClickNoButton() {
        popDirections()
    }
}