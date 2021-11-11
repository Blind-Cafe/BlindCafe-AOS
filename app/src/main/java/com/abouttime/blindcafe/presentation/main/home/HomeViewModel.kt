package com.abouttime.blindcafe.presentation.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.FCM_TAG
import com.abouttime.blindcafe.common.constants.LogTag.HOME_TAG
import com.abouttime.blindcafe.data.server.dto.notification.PostFcmDto
import com.abouttime.blindcafe.data.server.dto.z.PushNotificationDto
import com.abouttime.blindcafe.domain.use_case.GetHomeInfoUseCase
import com.abouttime.blindcafe.domain.use_case.PostFcmUseCase
import com.abouttime.blindcafe.domain.use_case.PostNotificationUseCase
import com.abouttime.blindcafe.presentation.main.MainFragmentDirections
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel(
    private val postNotificationUseCase: PostNotificationUseCase,
    private val postFcmUseCase: PostFcmUseCase,
    private val getHomeInfoUseCase: GetHomeInfoUseCase
): BaseViewModel() {
    private val _homeStatusCode: MutableLiveData<Int> = MutableLiveData<Int>(-1)
    val homeStatusCode: LiveData<Int> get() = _homeStatusCode


    init {
        Log.d(HOME_TAG, "getHomeInfo() 호출")
        //testHomeState()
        getHomeInfo()
    }

    private fun getHomeInfo() {
        getHomeInfoUseCase().onEach { resource ->
            when(resource) {
                is Resource.Loading -> {
                    Log.d(HOME_TAG, "Loading")
                }
                is Resource.Success -> {
                    Log.d(HOME_TAG, resource.data.toString())
                    resource.data?.matchingStatus?.let {
                        updateHomeState(it)
                    }


                }
                is Resource.Error -> {
                    Log.d(HOME_TAG, resource.message.toString())
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun updateHomeState(status: String) {
        when(status) {
            "NONE" -> _homeStatusCode.postValue(0)
            "WAIT" -> _homeStatusCode.postValue(1)
            "FOUND" -> _homeStatusCode.postValue(2)
            "MATCHING" -> _homeStatusCode.postValue(3)
        }

    }

    private fun testHomeState() = viewModelScope.launch {
        delay(5000)
        _homeStatusCode.postValue(0)
        delay(5000)
        _homeStatusCode.postValue(1)
        delay(5000)
        _homeStatusCode.postValue(2)
        delay(5000)
        _homeStatusCode.postValue(3)
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

    private fun postFcm(postFcmDto: PostFcmDto?) = viewModelScope.launch(Dispatchers.IO) {
        val targetToken = FirebaseMessaging.getInstance().token.await()
        Log.e(FCM_TAG, "$targetToken")
        postFcmUseCase(
            PostFcmDto(
                targetToken = targetToken,
                title = "테스트 제목",
                body = "테스트 바디",
                path = "테스트 패스"
            )
        )
    }



    /** onClick **/
    fun onClickTemporaryButton() {

        postFcm(null)
//        val firebaseToken = FirebaseMessaging.getInstance().token.await()
//        val notificationData = NotificationData("임시 title", "임시 message")
//        PushNotificationDto(notificationData, firebaseToken).also {
//            postNotification(it)
//        }
    }

    fun onClickCircleImageView() {
        moveToMatchingFragment()
    }



    /** navigation **/
    fun moveToMatchingFragment() {
        moveToDirections(MainFragmentDirections.actionMainFragmentToMatchingFragment())
    }



}