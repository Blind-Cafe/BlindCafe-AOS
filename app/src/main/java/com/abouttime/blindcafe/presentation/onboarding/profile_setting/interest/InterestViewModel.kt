package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest

import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.common.base.view_model.BaseViewModel

class InterestViewModel: BaseViewModel() {
    val selectedItemIdx = mutableListOf<Int>()


    fun getSelectedItemCount() = selectedItemIdx.size


}