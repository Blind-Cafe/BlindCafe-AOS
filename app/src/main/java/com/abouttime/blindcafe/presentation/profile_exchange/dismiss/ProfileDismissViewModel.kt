package com.abouttime.blindcafe.presentation.profile_exchange.dismiss

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel

class ProfileDismissViewModel: BaseViewModel() {

    private val _reason = MutableLiveData(0)
    val reason: LiveData<Int> get() = _reason



    fun onClickCheckButton(view: View) {
        when(view.id) {
            R.id.iv_check_1 -> _reason.value = 1
            R.id.iv_check_2 -> _reason.value = 2
            R.id.iv_check_3 -> _reason.value = 3
            R.id.iv_check_4 -> _reason.value = 4
            R.id.iv_check_5 -> _reason.value = 5
        }
    }

    fun onClickNextButton(v: View) {
        moveToDirections(
            ProfileDismissFragmentDirections.actionProfileDismissFragmentToConfirmDialogFragment(
                id = R.string.profile_dismiss_confirm_title,
                title = v.resources.getString(R.string.profile_dismiss_confirm_title),
                subtitle = v.resources.getString(R.string.profile_dismiss_confirm_subtitle),
                no = v.resources.getString(R.string.profile_dismiss_confirm_no),
                yes = v.resources.getString(R.string.profile_dismiss_confirm_yes)
            )
        )
    }

    private fun moveToReportDialogFragment() {

    }
}