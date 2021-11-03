package com.abouttime.blindcafe.presentation.onboarding.agreement

import com.abouttime.blindcafe.common.base.BaseViewModel

class AgreementViewModel: BaseViewModel() {


    val checks = mutableListOf(false, false, false, false)

    fun isAllChecked(): Boolean {
        for (i in checks.indices) {
            if (!checks[i]) return false
        }
        return true
    }


}