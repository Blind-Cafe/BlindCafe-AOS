package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel

class InterestViewModel(

) : BaseViewModel() {
    val selectedItemIdx = mutableListOf<Int>()


    fun canEnableNextButton() = selectedItemIdx.size == 3

    fun onClickNextButton() {
        if (canEnableNextButton()) {
            selectedItemIdx.sort()
            moveToDirections(InterestFragmentDirections.actionInterestFragmentToInterestSubFragment(
                i1 = selectedItemIdx[0]+1,
                i2 = selectedItemIdx[1]+1,
                i3 = selectedItemIdx[2]+1
            ))
        } else {
            showToast(R.string.profile_setting_toast_select_interest)
        }
    }




}