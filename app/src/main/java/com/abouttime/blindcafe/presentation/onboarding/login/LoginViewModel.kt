package com.abouttime.blindcafe.presentation.onboarding.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.view_model.BaseOnBoardingViewModel
import com.abouttime.blindcafe.common.constants.LogTag.LOGIN_TAG
import com.abouttime.blindcafe.data.remote.dto.KakaoToken
import com.abouttime.blindcafe.domain.use_case.PostKakaoTokenUseCase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel(
    private val postKakaoTokenUseCase: PostKakaoTokenUseCase
) : BaseOnBoardingViewModel() {

/**
    fun loginWithKakao(callback: (OAuthToken?, Throwable?) -> Unit) {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
    */

    fun postKakaoToken(kakaoToken: KakaoToken) = viewModelScope.launch(Dispatchers.IO) {


        postKakaoTokenUseCase(
            kakaoToken
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d(LOGIN_TAG, "loading 중")
                }
                is Resource.Success -> {
                    Log.d(LOGIN_TAG, "jwt ${result.data}")
                }
                is Resource.Error -> {
                    Log.d(LOGIN_TAG, "Error 중 ${result.message}")
                }
            }
        }.launchIn(viewModelScope)


    }





}