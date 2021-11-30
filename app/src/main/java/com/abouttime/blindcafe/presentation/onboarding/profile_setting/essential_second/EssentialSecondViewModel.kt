package com.abouttime.blindcafe.presentation.onboarding.profile_setting.essential_second

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey.MATCHING_SEX
import com.abouttime.blindcafe.common.constants.PreferenceKey.NICKNAME

class EssentialSecondViewModel: BaseViewModel() {
    private val _nickNameText = MutableLiveData<String>()
    val nickNameText: MutableLiveData<String> get() = _nickNameText


    private val _selectedSex = MutableLiveData(0)
    val selectedSex: LiveData<Int> get() = _selectedSex

    private val _enableNextButton = MutableLiveData(false)
    val enableNextButton: LiveData<Boolean> get() = _enableNextButton

    init {
        _selectedSex.value = 0
        _enableNextButton.value = false
        _enableNextButton.value = false
    }



    /** onClick **/
    fun onClickFemaleButton() {
        _selectedSex.value = 1
        checkInputAll()
    }
    fun onClickMaleButton() {
        _selectedSex.value = 2
        checkInputAll()
    }
    fun onClickBisexualButton() {
        _selectedSex.value = 3
        checkInputAll()
    }
    fun onClickNextButton() {
        if (isNickNameEmpty()) {
            showToast(R.string.profile_setting_toast_input_nickname)
            return
        }
        if (!isCorrectNickname()) {
            showToast(R.string.profile_setting_toast_input_again_nickname)
            return
        }
        if (!isSexSelected()) {
            showToast(R.string.profile_setting_toast_input_sex)
            return
        }
        val sex = if (_selectedSex.value == 1) {
            "F"
        } else if (_selectedSex.value == 2) {
            "M"
        } else {
            "N"
        }

        saveStringData(Pair(NICKNAME, _nickNameText.value ?: " "))
        saveStringData(Pair(MATCHING_SEX, sex))


        moveToDirections(EssentialSecondFragmentDirections.actionEssentialSecondFragmentToInterestFragment())

    }

    /** check **/
    fun checkInputAll() {
        if (!isCorrectNickname() || !isSexSelected()) {
            _enableNextButton.value = false
            return
        }
        _enableNextButton.value = true
    }


    /** boolean **/
    fun isNickNameEmpty(): Boolean = _nickNameText.value.isNullOrEmpty()
    fun isCorrectNickname(): Boolean = _nickNameText.value?.length in 1..9
    fun isSexSelected(): Boolean = (_selectedSex.value ?: 0) != 0



}