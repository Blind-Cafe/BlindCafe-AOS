package com.abouttime.blindcafe.presentation.main.my_page.setting.account_delete

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel

class AccountDeleteViewModel: BaseViewModel() {

    private val _reason = MutableLiveData(0)
    val reason: LiveData<Int> get() = _reason

    fun onClickYesButton() {

    }
    fun onClickNoButton() {

    }

    fun onClickCheckButton(view: View) {
        when(view.id) {
            R.id.iv_check_1 -> _reason.value = 1
            R.id.iv_check_2 -> _reason.value = 2
            R.id.iv_check_3 -> _reason.value = 3
            R.id.iv_check_4 -> _reason.value = 4
            R.id.iv_check_5 -> _reason.value = 5
        }
    }

    private fun moveToReportDialogFragment() {

    }

}