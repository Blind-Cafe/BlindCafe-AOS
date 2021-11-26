package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest

import android.util.Log
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.PreferenceKey.INTERESTS

class InterestViewModel(

) : BaseViewModel() {
    val selectedItemIdx = mutableListOf<Int>()


    fun canEnableNextButton() = selectedItemIdx.size == 3

    fun onClickNextButton() {
        if (canEnableNextButton()) {
            val interests = selectedItemIdx.sorted().joinToString(",") { (it+1).toString() }
            Log.d(RETROFIT_TAG, interests.toString())
            saveStringData(Pair(INTERESTS, interests))
            selectedItemIdx.clear()
            moveToDirections(InterestFragmentDirections.actionInterestFragmentToInterestSubFragment())
        } else {
            showToast(R.string.profile_setting_toast_select_interest)
        }
    }




}