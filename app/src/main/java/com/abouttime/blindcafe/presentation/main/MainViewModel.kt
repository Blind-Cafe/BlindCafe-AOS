package com.abouttime.blindcafe.presentation.main

import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.background.service.FirebaseService
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.data.remote.server.dto.user_info.device_token.PostDeviceTokenDto
import com.abouttime.blindcafe.domain.use_case.remote.server.PostDeviceTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val postDeviceTokenUseCase: PostDeviceTokenUseCase
): BaseViewModel() {

    var lastPageId = R.id.menu_home



    init {
        postDeviceToken()
    }

    private fun postDeviceToken() = viewModelScope.launch(Dispatchers.IO) {
        val deviceToken = FirebaseService.token
        deviceToken?.let {
            postDeviceTokenUseCase(
                PostDeviceTokenDto(deviceToken)
            ).onEach { result ->
                when (result) {
                    is Resource.Loading -> { showLoading() }
                    is Resource.Success -> { dismissLoading() }
                    is Resource.Error -> { dismissLoading() }
                }
            }.launchIn(viewModelScope)
        }

    }


}