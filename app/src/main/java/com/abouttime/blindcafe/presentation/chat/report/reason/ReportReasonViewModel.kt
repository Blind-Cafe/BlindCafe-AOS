package com.abouttime.blindcafe.presentation.chat.report.reason

import com.abouttime.blindcafe.common.base.BaseViewModel

class ReportReasonViewModel : BaseViewModel() {


    fun onClickYesButton() {
        moveToReportDialogFragment()
    }
    fun onClickNoButton() {
        popDirections()
    }

    private fun moveToReportDialogFragment() {
        moveToDirections(ReportReasonDialogFragmentDirections.actionReportReasonDialogFragmentToReportDialogFragment())
    }
}