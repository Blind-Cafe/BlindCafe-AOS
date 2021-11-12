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

) : BaseViewModel() {
    val selectedItemIdx = mutableListOf<Int>()


    fun canEnableNextButton() = selectedItemIdx.size == 3

    fun onClickNextButton() {
        if (canEnableNextButton()) {
            val interests = selectedItemIdx.sorted().joinToString(",") { it.toString() }
            Log.d(RETROFIT_TAG, interests.toString())
            saveStringData(Pair(INTERESTS, interests))
            //postUserInfo()
            // TODO 인자로 3개 리스트 넘기기!
            moveToDirections(InterestFragmentDirections.actionInterestFragmentToInterestSubFragment())
        } else {
            showToast(R.string.profile_setting_toast_select_interest)
        }
    }

    fun updateNextButton() {
        if (canEnableNextButton()) {

        }
    }




}