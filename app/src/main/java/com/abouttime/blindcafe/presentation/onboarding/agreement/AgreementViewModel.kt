package com.abouttime.blindcafe.presentation.onboarding.agreement

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey.INFO_INPUT

class AgreementViewModel : BaseViewModel() {
    private val _enableNextButton = MutableLiveData(false)
    val enableNextButton: LiveData<Boolean> get() = _enableNextButton

    private val _checks = MutableLiveData(arrayOf(false, false, false, false))
    val checks: LiveData<Array<Boolean>> get() = _checks


    fun isAllChecked() {
        _checks.value?.let { value ->
            for (i in value.indices) {
                if (!value[i]) {
                    _enableNextButton.value = false
                    return
                }
            }
            _enableNextButton.value = true
        }

    }

    fun onClickCheckButton(v: View) {
        val c = _checks.value
        when (v.id) {
            R.id.iv_check_1 -> c?.set(0, !c[0])
            R.id.iv_check_2 -> c?.set(1, !c[1])
            R.id.iv_check_3 -> c?.set(2, !c[2])
            R.id.iv_check_4 -> c?.set(3, !c[3])
        }
        _checks.value = c
        isAllChecked()
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