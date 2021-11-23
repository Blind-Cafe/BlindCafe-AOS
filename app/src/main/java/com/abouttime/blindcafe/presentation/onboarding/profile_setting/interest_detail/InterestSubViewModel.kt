package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY.INFO_INPUT
import com.abouttime.blindcafe.data.server.dto.interest.Interest
import com.abouttime.blindcafe.data.server.dto.user_info.UserInterest
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.domain.use_case.GetInterestUseCase
import com.abouttime.blindcafe.domain.use_case.PostUserInfoUseCase
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

    val selectedSubInterests = Array(3) { mutableListOf<String>() }

    init {
        try {
            val interests = getStringData(PREFERENCES_KEY.INTERESTS)?.split(",")
            Log.e(RETROFIT_TAG, interests.toString())
            val interest1 = interests?.get(0)?.toInt()
            val interest2 = interests?.get(1)?.toInt()
            val interest3 = interests?.get(2)?.toInt()
            Log.e(RETROFIT_TAG, "$interest1, $interest2, $interest3")
            if (interest1 != null && interest2 != null && interest3 != null) {
                Log.e(RETROFIT_TAG, "$interest1, $interest2, $interest3")
                getInterest(interest1, interest2, interest3)
            }
        } catch (e: Exception) {
            Log.e(RETROFIT_TAG, e.toString())
        }

    }

    val interestMap = mapOf(
        1 to "취업",
        2 to "만화/애니",
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
        getInterestUseCase(id1, id2, id3)
            .onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.e(RETROFIT_TAG, "Loading")
                    }
                    is Resource.Success -> {
                        result.data?.interests?.let {
                            Log.e(RETROFIT_TAG, "$it")
                            _interests.postValue(it)
                        }

                    }
                    is Resource.Error -> {
                        Log.e(RETROFIT_TAG, "${result?.message}")
                    }
                }
            }.launchIn(viewModelScope)
    }



    fun postUserInfo() = viewModelScope.launch(Dispatchers.IO) {

        val age = getStringData(PREFERENCES_KEY.AGE)?.toInt()
        val myGender = getStringData(PREFERENCES_KEY.SEX)
        val nickname = getStringData(PREFERENCES_KEY.NICKNAME)
        val partnerGender = getStringData(PREFERENCES_KEY.MATCHING_SEX)
        val interests = getStringData(PREFERENCES_KEY.INTERESTS)?.split(",")
        val interest1 = interests?.get(0)?.let { interestMap[it.toInt()] }
        val interest2 = interests?.get(1)?.let { interestMap[it.toInt()] }
        val interest3 = interests?.get(2)?.let { interestMap[it.toInt()] }
        val subInterests1 = selectedSubInterests[0].map { interest1+(it + 1) }
        val subInterests2 = selectedSubInterests[1].map { interest2+(it + 1) }
        val subInterests3 = selectedSubInterests[2].map { interest3+(it + 1) }

        val dto =  PostUserInfoDto(
            age = age,
            myGender = myGender,
            nickname = nickname,
            partnerGender = partnerGender,
            userInterests = listOf(
                UserInterest(
                    main = interests?.get(0)?.toInt() ?: 0,
                    sub = selectedSubInterests[0]
                ),
                UserInterest(
                    main = interests?.get(1)?.toInt() ?: 0,
                    sub =  selectedSubInterests[1]
                ),
                UserInterest(
                    main = interests?.get(2)?.toInt() ?: 0,
                    sub =  selectedSubInterests[2]
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
                        saveStringData(Pair(INFO_INPUT, response.data.code))
                        moveToSigninFragment()
                    }
                }
                is Resource.Error -> {
                    Log.d(LogTag.RETROFIT_TAG, response.message.toString())
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


    private fun moveToSigninFragment() {
        moveToDirections(InterestSubFragmentDirections.actionInterestSubFragmentToSigninFragment())
    }
}