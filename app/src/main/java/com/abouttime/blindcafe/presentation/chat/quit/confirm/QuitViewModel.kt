package com.abouttime.blindcafe.presentation.chat.quit.confirm

import com.abouttime.blindcafe.common.base.BaseViewModel

class QuitViewModel: BaseViewModel() {

    fun onClickYesButton() {
        popDirections()
    }
    fun onClickNoButton() {
        popDirections()
    }
}