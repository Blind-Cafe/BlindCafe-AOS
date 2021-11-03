package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY.INTERESTS

class InterestViewModel: BaseViewModel() {
    val selectedItemIdx = mutableListOf<Int>()


    fun getSelectedItemCount() = selectedItemIdx.size

    fun onClickNextButton() {
        if (getSelectedItemCount() >= 3) {
            val interests = selectedItemIdx.sorted().joinToString(",") { it.toString() }
            saveStringData(Pair(INTERESTS, interests))

            // TODO 인자로 3개 리스트 넘기기!
            moveToDirections(InterestFragmentDirections.actionInterestFragmentToInterestDetailFragment())
        } else {
            showToast(R.string.profile_setting_toast_select_interest)
        }
    }


}