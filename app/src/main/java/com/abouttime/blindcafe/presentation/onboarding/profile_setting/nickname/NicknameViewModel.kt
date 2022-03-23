package com.abouttime.blindcafe.presentation.onboarding.profile_setting.nickname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey.AGE
import com.abouttime.blindcafe.common.constants.PreferenceKey.SEX
import java.lang.Exception

class NicknameViewModel: BaseViewModel() {

    private val _ageText = MutableLiveData<String>()
    val ageText: MutableLiveData<String> get() = _ageText


    private val _selectedSex = MutableLiveData(0)
    val selectedSex: LiveData<Int> get() = _selectedSex

    private val _enableNextButton = MutableLiveData(false)
    val enableNextButton: LiveData<Boolean> get() = _enableNextButton



    init {
        _ageText.value = ""
        _selectedSex.value = 0
        _enableNextButton.value = false
    }

    fun updateToFirst() {

    }



    fun checkInputAll() {
        if (isAgeEmpty() || !isCorrectAge() || !isSexSelected()) {
            _enableNextButton.value = false
            return
        }
        _enableNextButton.value = true
    }


   // onClick---

    fun onClickFemaleButton() {
        _selectedSex.value = 1
        checkInputAll()
    }
    fun onClickMaleButton() {
        _selectedSex.value = 2
        checkInputAll()
    }


    fun onClickNextButton() {
        if (isAgeEmpty()) {
            showToast(R.string.profile_setting_toast_input_age)
            return
        }
        if (!isCorrectAge()) {
            showToast(R.string.profile_setting_toast_input_correct_age)
            return
        }
        if (!isSexSelected()) {
            showToast(R.string.profile_setting_toast_input_sex)
            return
        }
        val sex = if (_selectedSex.value == 1) {
            "F"
        } else {
            "M"
        }
        saveStringData(Pair(AGE, _ageText.value ?: ""))
        saveStringData(Pair(SEX, sex))

        moveToDirections(NicknameFragmentDirections.actionNicknameFragmentToGenderAgeFragment())

    }

    fun onClickBackButton() {
        popDirections()
    }

    // boolean ---

    fun isCorrectAge(): Boolean {
        try {
            val input = ageText.value
            val age = input?.toInt()

            if ((age ?:0) < 19) {
                return false
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }
    fun isAgeEmpty(): Boolean = ageText.value.isNullOrEmpty()
    fun isSexSelected(): Boolean = _selectedSex.value != 0








}