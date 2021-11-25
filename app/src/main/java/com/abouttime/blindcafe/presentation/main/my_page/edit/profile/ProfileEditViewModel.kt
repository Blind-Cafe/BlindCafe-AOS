package com.abouttime.blindcafe.presentation.main.my_page.edit.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.data.server.dto.user_info.profile.GetProfileInfoDto
import com.abouttime.blindcafe.domain.use_case.server.GetProfileInfoUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileEditViewModel(
    private val getProfileInfoUseCase: GetProfileInfoUseCase
): BaseViewModel() {

    private val _profileInfo = SingleLiveData<GetProfileInfoDto>()
    val profileInfo: SingleLiveData<GetProfileInfoDto> get() = _profileInfo

    init {
        getProfileInfo()
    }

    /** use cases **/
    private fun getProfileInfo() {
        getProfileInfoUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> { showLoading() }
                is Resource.Success -> {
                    result.data?.let { dto ->
                        _profileInfo.value = dto
                    }
                }
                is Resource.Error -> {
                    Log.e(RETROFIT_TAG, result.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onClickCompleteButton() {
        // TODO 프로필 수정 api 연결
        popDirections()
    }

}