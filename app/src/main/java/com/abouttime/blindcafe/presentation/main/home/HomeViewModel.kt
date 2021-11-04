package com.abouttime.blindcafe.presentation.main.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.FCM
import com.abouttime.blindcafe.common.constants.Url.FCM_MESSAGE_TOPIC
import com.abouttime.blindcafe.data.remote.dto.NotificationData
import com.abouttime.blindcafe.data.remote.dto.PushNotification
import com.abouttime.blindcafe.domain.use_case.PostNotificationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val postNotificationUseCase: PostNotificationUseCase
): BaseViewModel() {


    fun onClickTemporaryButton() {
        val notificationData = NotificationData("임시 title", "임시 message")
        PushNotification(notificationData, FCM_MESSAGE_TOPIC).also {
            postNotification(it)
        }
    }

    fun postNotification(notification: PushNotification) = viewModelScope.launch(Dispatchers.IO) {
        postNotificationUseCase(
            notification
        ).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    Log.d(FCM, "loading")
                }
                is Resource.Success -> {
                    Log.d(FCM, "success ${result.data.toString()}  ")
                }
                is Resource.Error -> {
                    Log.d(FCM, "error")
                }
            }
        }.launchIn(viewModelScope)
    }



}