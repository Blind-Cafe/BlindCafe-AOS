package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey
import com.abouttime.blindcafe.common.constants.PreferenceKey.MAIN_INTEREST
import com.abouttime.blindcafe.common.constants.PreferenceKey.SUB_INTEREST1
import com.abouttime.blindcafe.common.constants.PreferenceKey.SUB_INTEREST2
import com.abouttime.blindcafe.common.constants.PreferenceKey.SUB_INTEREST3
import com.abouttime.blindcafe.data.server.dto.interest.Interest
import com.abouttime.blindcafe.data.server.dto.user_info.UserInterest
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.domain.use_case.server.GetInterestUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PartnerGenderViewModel(

): BaseViewModel() {


}