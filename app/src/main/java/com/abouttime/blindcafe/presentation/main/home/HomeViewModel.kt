package com.abouttime.blindcafe.presentation.main.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.FCM_TAG
import com.abouttime.blindcafe.data.server.dto.NotificationData
import com.abouttime.blindcafe.data.server.dto.PushNotificationDto
import com.abouttime.blindcafe.domain.use_case.PostNotificationUseCase
import com.abouttime.blindcafe.presentation.main.MainFragmentDirections
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel(
    private val postNotificationUseCase: PostNotificationUseCase
): BaseViewModel() {


    fun onClickTemporaryButton() = viewModelScope.launch(Dispatchers.IO) {
        val firebaseToken = FirebaseMessaging.getInstance().token.await()
        val notificationData = NotificationData("임시 title", "임시 message")
        PushNotificationDto(notificationData, firebaseToken).also {
            postNotification(it)
        }
    }

    private suspend fun postNotification(notificationDto: PushNotificationDto) {
        postNotificationUseCase(
            notificationDto
        ).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    Log.d(FCM_TAG, "loading")
                }
                is Resource.Success -> {
                    Log.d(FCM_TAG, "success ${result.data.toString()}  ")
                }
                is Resource.Error -> {
                    Log.d(FCM_TAG, "error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onClickCircleImageView() {
        moveToMatchingFragment()
    }


    fun moveToMatchingFragment() {
        moveToDirections(MainFragmentDirections.actionMainFragmentToMatchingFragment())
    }



}