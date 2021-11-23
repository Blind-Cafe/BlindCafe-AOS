package com.abouttime.blindcafe.presentation.chat.report

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel

class ReportReasonViewModel : BaseViewModel() {
    private val _reason = MutableLiveData(0)
    val reason: LiveData<Int> get() = _reason

    var matchingId: Int? = null

    fun onClickYesButton(v: View) {
        if (canClickNextButton()) {
            if (reason.value != null && matchingId != null) {
                moveToDirections(
                    ReportReasonDialogFragmentDirections.actionReportReasonDialogFragmentToConfirmDialogFragment(
                        id = R.string.report_confirm_title,
                        title = v.resources.getString(R.string.report_confirm_title),
                        subtitle = v.resources.getString(R.string.report_confirm_subtitle),
                        no = v.resources.getString(R.string.report_confirm_no),
                        yes = v.resources.getString(R.string.report_confirm_yes),
                        reason = _reason.value ?: 0,
                        matchingId = matchingId ?: 0
                    )
                )
            } else {
                showToast(R.string.toast_fail)
            }
        } else {
            showToast(R.string.toast_select_reason)
        }

    }

    fun onClickNoButton() {
        popDirections()
    }

    fun onClickCheckButton(view: View) {
        when (view.id) {
            R.id.iv_check_1 -> _reason.value = 1
            R.id.iv_check_2 -> _reason.value = 2
            R.id.iv_check_3 -> _reason.value = 3
            R.id.iv_check_4 -> _reason.value = 4
            R.id.iv_check_5 -> _reason.value = 5
        }
    }

    fun canClickNextButton() = _reason.value != 0


}