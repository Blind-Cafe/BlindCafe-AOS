package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest

import android.util.Log
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY.INTERESTS

class InterestViewModel(

) : BaseViewModel() {
    val selectedItemIdx = mutableListOf<Int>()


    fun canEnableNextButton() = selectedItemIdx.size == 3

    fun onClickNextButton() {
        if (canEnableNextButton()) {
            val interests = selectedItemIdx.sorted().joinToString(",") { it.toString() }
            Log.d(RETROFIT_TAG, interests.toString())
            saveStringData(Pair(INTERESTS, interests))
            //postUserInfo()
            // TODO 인자로 3개 리스트 넘기기!
            moveToDirections(InterestFragmentDirections.actionInterestFragmentToInterestSubFragment())
        } else {
            showToast(R.string.profile_setting_toast_select_interest)
        }
    }

    fun updateNextButton() {
        if (canEnableNextButton()) {

        }
    }




}