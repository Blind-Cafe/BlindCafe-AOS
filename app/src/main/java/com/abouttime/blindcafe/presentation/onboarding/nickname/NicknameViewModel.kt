package com.abouttime.blindcafe.presentation.onboarding.nickname

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey.MATCHING_SEX
import com.abouttime.blindcafe.common.constants.PreferenceKey.NICKNAME

class NicknameViewModel: BaseViewModel() {

    private val _nickNameText = MutableLiveData<String>()
    val nickNameText: MutableLiveData<String> get() = _nickNameText

    private val _enableNextButton = MutableLiveData(false)
    val enableNextButton: LiveData<Boolean> get() = _enableNextButton

    init {
        _enableNextButton.value = false
    }



    /** onClick **/
    fun onClickNextButton() {
        if (isNickNameEmpty()) {
            showToast(R.string.profile_setting_toast_input_nickname)
            return
        }

        saveStringData(Pair(NICKNAME, _nickNameText.value ?: " "))
        moveToDirections(NicknameFragmentDirections.actionNicknameFragmentToGenderAgeFragment())
    }

    fun onClickBackButton() {
        popDirections()
    }

    /** check **/
    fun checkInputAll() {
        if (!isCorrectNickname()) {
            _enableNextButton.value = false
            return
        }
        _enableNextButton.value = true
    }


    /** boolean **/
    fun isNickNameEmpty(): Boolean = _nickNameText.value.isNullOrEmpty()
    fun isCorrectNickname(): Boolean = _nickNameText.value?.length in 1..10

}