package com.abouttime.blindcafe.presentation.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.data.server.dto.user_info.device_token.PostDeviceTokenDto
import com.abouttime.blindcafe.domain.use_case.server.PostDeviceTokenUseCase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel(
    private val postDeviceTokenUseCase: PostDeviceTokenUseCase
): BaseViewModel() {

    var lastPageId = R.id.menu_home



    init {
        Log.d(LogTag.RETROFIT_TAG, "deviceToken")
        postDeviceToken()
    }

    private fun postDeviceToken() = viewModelScope.launch(Dispatchers.IO) {
        val deviceToken = FirebaseMessaging.getInstance().token.await()
        Log.d(LogTag.RETROFIT_TAG, deviceToken)
        postDeviceTokenUseCase(
            PostDeviceTokenDto(deviceToken)
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> { Log.d(LogTag.RETROFIT_TAG, "Loading")}
                is Resource.Success -> { Log.d(LogTag.RETROFIT_TAG, result.data.toString())}
                is Resource.Error -> { Log.d(LogTag.RETROFIT_TAG, result.message.toString())}
            }

        }.launchIn(viewModelScope)
    }


}