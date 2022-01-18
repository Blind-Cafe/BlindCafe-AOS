package com.abouttime.blindcafe.presentation.onboarding.signin

import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey
import com.abouttime.blindcafe.common.constants.PreferenceKey.MAIN_INTEREST
import com.abouttime.blindcafe.common.constants.PreferenceKey.SUB_INTEREST1
import com.abouttime.blindcafe.common.constants.PreferenceKey.SUB_INTEREST2
import com.abouttime.blindcafe.common.constants.PreferenceKey.SUB_INTEREST3
import com.abouttime.blindcafe.data.remote.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.data.remote.server.dto.user_info.UserInterest
import com.abouttime.blindcafe.domain.use_case.remote.server.PostUserInfoUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SigninViewModel(
    private val postUserInfoUseCase: PostUserInfoUseCase
) : BaseViewModel() {


    /** use cases **/
    private fun postUserInfo(dto: PostUserInfoDto) {
        postUserInfoUseCase(
            dto
        ).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    saveStringData(Pair(PreferenceKey.INFO_INPUT, response.data?.code ?: "o"))
                    moveToMainFragment()
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


    /** onClick **/
    fun onClickBackButton() {
        popDirections()
    }
    fun onClickNextButton() {
        makeDto()?.let { dto ->
            postUserInfo(dto)
        }

    }

    /** navigate **/
    private fun moveToMainFragment() {
        moveToDirections(SigninFragmentDirections.actionSigninFragmentToMainFragment())
    }


    /** etc **/
    fun makeDto(): PostUserInfoDto? {
        val age = getStringData(PreferenceKey.AGE)?.toInt()
        val myGender = getStringData(PreferenceKey.SEX)
        val nickname = getStringData(PreferenceKey.NICKNAME)
        val partnerGender = getStringData(PreferenceKey.MATCHING_SEX)
        val mainInterest = getStringData(MAIN_INTEREST)?.split(",")
        val subInterest1 = getStringData(SUB_INTEREST1)?.split(",")
        val subInterest2 = getStringData(SUB_INTEREST2)?.split(",")
        val subInterest3 = getStringData(SUB_INTEREST3)?.split(",")


        if (age != null
            && myGender != null
            && nickname != null
            && partnerGender != null
            && mainInterest != null
            && subInterest1 != null
            && subInterest2 != null
            && subInterest3 != null) {

            return PostUserInfoDto(
                age = age,
                myGender = myGender,
                nickname = nickname,
                partnerGender = partnerGender,
                userInterests = listOf(
                    UserInterest(
                        main = mainInterest[0].toInt(),
                        sub = subInterest1
                    ),
                    UserInterest(
                        main = mainInterest[1].toInt(),
                        sub =  subInterest2
                    ),
                    UserInterest(
                        main = mainInterest[2].toInt(),
                        sub =  subInterest3
                    )
                )
            )

        } else {
            showToast(R.string.toast_alert_input_first_info)
            return null
        }


    }
}