package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.PreferenceKey
import com.abouttime.blindcafe.common.constants.PreferenceKey.INFO_INPUT
import com.abouttime.blindcafe.data.server.dto.interest.Interest
import com.abouttime.blindcafe.data.server.dto.user_info.UserInterest
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.domain.use_case.server.GetInterestUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostUserInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class InterestSubViewModel(
    private val getInterestUseCase: GetInterestUseCase,
    private val postUserInfoUseCase: PostUserInfoUseCase
): BaseViewModel() {

    private val _nextButton = MutableLiveData(false)
    val nextButton: LiveData<Boolean> get() = _nextButton

    private val _interests = SingleLiveData<List<Interest>>()
    val interests: SingleLiveData<List<Interest>> get() = _interests

    var selectedSubInterests = Array(3) { mutableListOf<String>() }



    var i1 = 0
    var i2 = 0
    var i3 = 0

    val interestMap = mapOf(
        1 to "취업",
        2 to "작품",
        3 to "동물",
        4 to "음식",
        5 to "여행",
        6 to "게임",
        7 to "연예",
        8 to "스포츠",
        9 to "재테크"
    )


    fun updateNextButton() {
      _nextButton.value = canEnableNextButton()
    }

    private fun canEnableNextButton(): Boolean {
        if (selectedSubInterests[0].isNotEmpty() && selectedSubInterests[1].isNotEmpty() && selectedSubInterests[2].isNotEmpty()) {
            return true
        }
        return false
    }


    fun getInterest(id1 : Int, id2: Int, id3: Int) {
        i1 = id1
        i2 = id2
        i3 = id3
        getInterestUseCase(id1, id2, id3)
            .onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        result.data?.interests?.let {
                            selectedSubInterests = Array(3) { mutableListOf<String>() }
                            _nextButton.value = false
                            _interests.postValue(it)
                        }
                        dismissLoading()
                    }

                    is Resource.Error -> {
                        if (result.message == "400") {
                            showToast(R.string.toast_fail)
                        } else {
                            showToast(R.string.toast_check_internet)
                        }
                        dismissLoading()
                    }
                }
            }.launchIn(viewModelScope)
    }



    fun postUserInfo() = viewModelScope.launch(Dispatchers.IO) {

        val age = getStringData(PreferenceKey.AGE)?.toInt()
        val myGender = getStringData(PreferenceKey.SEX)
        val nickname = getStringData(PreferenceKey.NICKNAME)
        val partnerGender = getStringData(PreferenceKey.MATCHING_SEX)

        val dto =  PostUserInfoDto(
            age = age,
            myGender = myGender,
            nickname = nickname,
            partnerGender = partnerGender,
            userInterests = listOf(
                UserInterest(
                    main = i1,
                    sub = selectedSubInterests[0]
                ),
                UserInterest(
                    main = i2,
                    sub =  selectedSubInterests[1]
                ),
                UserInterest(
                    main = i3,
                    sub =  selectedSubInterests[2]
                )
            )
        )

        Log.d(RETROFIT_TAG, dto.toString())

        postUserInfoUseCase(
            dto
        ).onEach { response ->
            when (response){
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    Log.d(RETROFIT_TAG, "Success ${response.data?.code} ${response.data?.message}")
                    val code = response.data?.code?.toInt()
                    if (code == 1000) {
                        saveStringData(Pair(INFO_INPUT, response.data.code))
                        moveToSigninFragment()
                    }
                    dismissLoading()
                }
                is Resource.Error -> {
                    if (response.message == "400") {
                        showToast(R.string.toast_fail)
                    } else {
                        showToast(R.string.toast_check_internet)
                    }
                    dismissLoading()
                }
            }

        }.launchIn(viewModelScope)

    }

    fun onClickNextButton() {
        if (canEnableNextButton()) {
            postUserInfo()
        } else {
            showToast(R.string.profile_setting_toast_select_sub_interest)
        }
    }

    fun onClickBackButton() {
        popDirections()
    }


    private fun moveToSigninFragment() {
        moveToDirections(InterestSubFragmentDirections.actionInterestSubFragmentToSigninFragment())
    }
}