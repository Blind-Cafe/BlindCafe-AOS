package com.abouttime.blindcafe.presentation.onboarding.agreement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey.INFO_INPUT

class AgreementViewModel : BaseViewModel() {
    private val _enableNextButton = MutableLiveData(false)
    val enableNextButton: LiveData<Boolean> get() = _enableNextButton

    var checks = mutableListOf(false, false, false, false)

    init {
        checks = mutableListOf(false, false, false, false)
    }

    fun isAllChecked() {
        for (i in checks.indices) {
            if (!checks[i]) {
                _enableNextButton.value = false
                return
            }
        }
        _enableNextButton.value = true
    }

    fun onClickNextButton() {
        if (_enableNextButton.value == true) {
            val infoInput = getStringData(INFO_INPUT)
            if (infoInput.isNullOrEmpty()) {
                moveToProfileInput()
            } else {
                moveToMainFragment()
            }
        } else {
            showToast(R.string.agreement_toast_alert_select_all)
        }
    }


    private fun moveToProfileInput() {
        moveToDirections(AgreementFragmentDirections.actionAgreementFragmentToProfileSettingFragment())
    }

    private fun moveToMainFragment() {
        moveToDirections(AgreementFragmentDirections.actionAgreementFragmentToMainFragment())
    }


}