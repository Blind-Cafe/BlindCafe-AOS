package com.abouttime.blindcafe.presentation.chat.quit

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel

class QuitReasonViewModel: BaseViewModel() {
    private val _reason = MutableLiveData(0)
    val reason: LiveData<Int> get() = _reason



    fun onClickCheckButton(view: View) {
        when(view.id) {
            R.id.iv_check_1 -> _reason.value = 1
            R.id.iv_check_2 -> _reason.value = 2
            R.id.iv_check_3 -> _reason.value = 3
            R.id.iv_check_4 -> _reason.value = 4
        }
    }

    fun onClickYesButton(v: View) {
        if (canClickNextButton()) {
            moveToDirections(
                QuitReasonDialogFragmentDirections.actionQuitReasonDialogFragmentToConfirmDialogFragment(
                    id = R.string.quit_confirm_title,
                    title = v.resources.getString(R.string.quit_confirm_title),
                    subtitle = v.resources.getString(R.string.quit_confirm_subtitle),
                    no = v.resources.getString(R.string.quit_confirm_no),
                    yes = v.resources.getString(R.string.quit_confirm_yes)
                )
            )
        } else {
            showToast(R.string.toast_select_reason)
        }
    }
    fun onClickNoButton() {
        popDirections()
    }


    fun canClickNextButton() = _reason.value != 0
}