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
            moveToInterestSubEditFragment(i1, i2, i3)

        } else {
            showToast(R.string.profile_setting_toast_select_interest)
        }
    }

    private fun moveToInterestSubEditFragment(i1: Int, i2: Int, i3: Int) {
        moveToDirections(InterestEditFragmentDirections.actionInterestEditFragmentToInterestSubEditFragment(
            mainInterest1 = i1+1,
            mainInterest2 = i2+1,
            mainInterest3 = i3+1
        ))
    }


}