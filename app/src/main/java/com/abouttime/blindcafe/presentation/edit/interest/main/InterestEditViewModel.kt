package com.abouttime.blindcafe.presentation.edit.interest.main

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel

class InterestEditViewModel: BaseViewModel() {

    val selectedItemIdx = mutableListOf<Int>()


    fun canEnableNextButton() = selectedItemIdx.size == 3

    fun onClickNextButton() {
        if (canEnableNextButton()) {
            val i1 = selectedItemIdx[0]
            val i2 = selectedItemIdx[1]
            val i3 = selectedItemIdx[2]
            selectedItemIdx.clear()
            // TODO 관심사 수정

        } else {
            showToast(R.string.profile_setting_toast_select_interest)
        }
    }



    fun onClickBackButton() {
        popDirections()
    }


}