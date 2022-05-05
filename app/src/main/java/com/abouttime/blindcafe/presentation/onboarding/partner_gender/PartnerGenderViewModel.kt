package com.abouttime.blindcafe.presentation.onboarding.partner_gender

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey
import com.abouttime.blindcafe.presentation.onboarding.gender_age.GenderAgeFragmentDirections

class PartnerGenderViewModel: BaseViewModel() {
    private val _selectedSex = MutableLiveData(0)
    val selectedSex: LiveData<Int> get() = _selectedSex

    private val _enableNextButton = MutableLiveData(false)
    val enableNextButton: LiveData<Boolean> get() = _enableNextButton

    init {
        _selectedSex.value = 0
        _enableNextButton.value = false
    }

    private fun checkInputAll() {
        if (!isSexSelected()) {
            _enableNextButton.value = false
            return
        }
        _enableNextButton.value = true
    }

    fun onClickFemaleButton() {
        _selectedSex.value = 1
        checkInputAll()
    }
    fun onClickMaleButton() {
        _selectedSex.value = 2
        checkInputAll()
    }
    fun onClickBiSexualButton() {
        _selectedSex.value = 3
        checkInputAll()
    }


    fun onClickNextButton() {
        if (!isSexSelected()) {
            showToast(R.string.profile_setting_toast_input_sex)
            return
        }
        val sex = if (_selectedSex.value == 1) {
            "F"
        } else if (_selectedSex.value == 2) {
            "M"
        }  else {
            "N"
        }
        saveStringData(Pair(PreferenceKey.MATCHING_SEX, sex))

        moveToDirections(GenderAgeFragmentDirections.actionGenderAgeFragmentToPartnerGenderFragment())
    }

    fun onClickBackButton() {
        popDirections()
    }

    private fun isSexSelected(): Boolean = _selectedSex.value != 0
}