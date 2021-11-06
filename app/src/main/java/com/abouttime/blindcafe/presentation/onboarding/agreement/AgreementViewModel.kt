package com.abouttime.blindcafe.presentation.onboarding.agreement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel

class AgreementViewModel: BaseViewModel() {
    private val _enableNextButton = MutableLiveData(false)
    val enableNextButton: LiveData<Boolean> get() = _enableNextButton

    val checks = mutableListOf(false, false, false, false)

    fun isAllChecked() {
        for (i in checks.indices) {
            if (!checks[i]) {
                _enableNextButton.value = false
                return
            }
        }
        _enableNextButton.value = true
    }


}