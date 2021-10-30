package com.abouttime.blindcafe.presentation.onboarding.agreement

import com.abouttime.blindcafe.common.base.view_model.BaseOnBoardingViewModel

class AgreementViewModel: BaseOnBoardingViewModel() {


    val checks = mutableListOf(false, false, false, false)

    fun isAllChecked(): Boolean {
        for (i in checks.indices) {
            if (!checks[i]) return false
        }
        return true
    }


}