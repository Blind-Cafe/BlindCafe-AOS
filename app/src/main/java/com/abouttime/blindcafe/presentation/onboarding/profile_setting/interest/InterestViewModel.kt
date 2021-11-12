package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.LogTag.USER_INFO_TAG
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY.AGE
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY.INFO_INPUT
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY.INTERESTS
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY.MATCHING_SEX
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY.NICKNAME
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY.SEX
import com.abouttime.blindcafe.data.server.dto.user_info.Interest
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.domain.use_case.PostUserInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class InterestViewModel(
    private val postUserInfoUseCase: PostUserInfoUseCase,
) : BaseViewModel() {
    val selectedItemIdx = mutableListOf<Int>()


    fun getSelectedItemCount() = selectedItemIdx.size

    fun onClickNextButton() {
        if (getSelectedItemCount() >= 3) {
            val interests = selectedItemIdx.sorted().joinToString(",") { it.toString() }
            Log.d(RETROFIT_TAG, interests.toString())
            saveStringData(Pair(INTERESTS, interests))
            postUserInfo()
            // TODO 인자로 3개 리스트 넘기기!
            //moveToDirections(InterestFragmentDirections.actionInterestFragmentToMainFragment())
        } else {
            showToast(R.string.profile_setting_toast_select_interest)
        }
    }

    fun postUserInfo() = viewModelScope.launch(Dispatchers.IO) {

        val age = getStringData(AGE)?.toInt()
        val myGender = getStringData(SEX)
        val nickname = getStringData(NICKNAME)
        val partnerGender = getStringData(MATCHING_SEX)
        val interests = getStringData(INTERESTS)?.split(",")
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

        Log.d(RETROFIT_TAG, dto.toString())

        postUserInfoUseCase(
           dto
        ).onEach { response ->
            when (response){
                is Resource.Loading -> {
                    Log.d(RETROFIT_TAG, "Loading")
                }
                is Resource.Success -> {
                    Log.d(RETROFIT_TAG, "Success ${response.data?.code} ${response.data?.message}")
                    val code = response.data?.code?.toInt()
                    if (code == 1000) {
                        saveStringData(Pair(INFO_INPUT, response.data.code ?: ""))
                        moveToDirections(InterestFragmentDirections.actionInterestFragmentToMainFragment())
                    }

                }
                is Resource.Error -> {
                    Log.d(RETROFIT_TAG, "Error")
                }
            }

        }.launchIn(viewModelScope)

    }


}