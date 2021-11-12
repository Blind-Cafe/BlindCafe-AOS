package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY
import com.abouttime.blindcafe.data.server.dto.user_info.Interest
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.domain.use_case.PostUserInfoUseCase
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest.InterestFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class InterestSubViewModel(
    private val postUserInfoUseCase: PostUserInfoUseCase
): BaseViewModel() {

    private val _nextButton = MutableLiveData(false)
    val nextButton: LiveData<Boolean> get() = _nextButton

    val selectedSubInterests = Array(3) { mutableListOf<Int>() }


    fun updateNextButton() {
      _nextButton.value = canEnableNextButton()
    }
    private fun canEnableNextButton(): Boolean {
        for (i in selectedSubInterests.indices) {
            if (selectedSubInterests[i].isEmpty().not()) {
                return true
            }
        }
        return false
    }



    fun postUserInfo() = viewModelScope.launch(Dispatchers.IO) {

        val age = getStringData(PREFERENCES_KEY.AGE)?.toInt()
        val myGender = getStringData(PREFERENCES_KEY.SEX)
        val nickname = getStringData(PREFERENCES_KEY.NICKNAME)
        val partnerGender = getStringData(PREFERENCES_KEY.MATCHING_SEX)
        val interests = getStringData(PREFERENCES_KEY.INTERESTS)?.split(",")
        // TODO 세부관심사 저장! +  val infoInput = getStringData(INFO_INPUT) 을 위한 INFO_INPUT 여부 저장!
        val dto =  PostUserInfoDto(
            age = age,
            myGender = myGender,
            nickname = nickname,
            partnerGender = partnerGender,
            interests = listOf(
                Interest(
                    main = interests?.get(0)?.toInt()?.plus(1) ?: 1,
                    sub = listOf("음식1", "음식2", "음식3")
                ),
                Interest(
                    main = interests?.get(1)?.toInt()?.plus(1) ?: 2,
                    sub = listOf("여행1", "여행2", "여행3")
                ),
                Interest(
                    main = interests?.get(2)?.toInt()?.plus(1) ?: 3,
                    sub = listOf("게임1", "게임2", "게임3")
                )
            )
        )

        Log.d(LogTag.RETROFIT_TAG, dto.toString())

        postUserInfoUseCase(
            dto
        ).onEach { response ->
            when (response){
                is Resource.Loading -> {
                    Log.d(LogTag.RETROFIT_TAG, "Loading")
                }
                is Resource.Success -> {
                    Log.d(LogTag.RETROFIT_TAG, "Success ${response.data?.code} ${response.data?.message}")
                    val code = response.data?.code?.toInt()
                    if (code == 1000) {
                        saveStringData(Pair(PREFERENCES_KEY.INFO_INPUT, response.data.code ?: ""))
                        moveToDirections(InterestSubFragmentDirections.actionInterestSubFragmentToSigninFragment())
                    }

                }
                is Resource.Error -> {
                    Log.d(LogTag.RETROFIT_TAG, "Error")
                }
            }

        }.launchIn(viewModelScope)

    }

    fun onClickNextButton() {
        if (canEnableNextButton()) {
            moveToSigninFragment()
        } else {
            showToast(R.string.profile_setting_toast_select_sub_interest)
        }
    }


    private fun moveToSigninFragment() {
        moveToDirections(InterestSubFragmentDirections.actionInterestSubFragmentToSigninFragment())
    }
}