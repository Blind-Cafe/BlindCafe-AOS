package com.abouttime.blindcafe.presentation.chat.quit.reason

import com.abouttime.blindcafe.common.base.BaseViewModel

class QuitReasonViewModel: BaseViewModel() {

    fun onClickYesButton() {
        moveToQuitDialogFragment()
    }
    fun onClickNoButton() {
        popDirections()
    }

    private fun moveToQuitDialogFragment() {
        moveToDirections(QuitReasonDialogFragmentDirections.actionQuitReasonDialogFragmentToQuitDialogFragment())
    }
}