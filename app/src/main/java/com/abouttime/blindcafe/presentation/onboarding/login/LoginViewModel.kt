package com.abouttime.blindcafe.presentation.onboarding.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.data.server.dto.KakaoToken
import com.abouttime.blindcafe.domain.use_case.PostKakaoTokenUseCase
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(
    private val postKakaoTokenUseCase: PostKakaoTokenUseCase
) : BaseViewModel() {


    private val _loginStateEvent = MutableLiveData<LoginState>(LoginState.Uninitialized)
    val loginStateEvent: LiveData<LoginState> get() = _loginStateEvent



    fun loginWithKakao(context: Context, callback: (OAuthToken?, Throwable?) -> Unit) {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }


    fun postKakaoToken(kakaoToken: KakaoToken) = viewModelScope.launch(Dispatchers.IO) {

        val firebaseToken = FirebaseMessaging.getInstance().token.await()




        postKakaoTokenUseCase(
            kakaoToken
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _loginStateEvent.postValue(LoginState.Loading)
                }
                is Resource.Success -> {
                    _loginStateEvent.postValue(LoginState.Success)
                }
                is Resource.Error -> {
                    _loginStateEvent.postValue(LoginState.Error)
                }
            }
        }.launchIn(viewModelScope)


    }







}