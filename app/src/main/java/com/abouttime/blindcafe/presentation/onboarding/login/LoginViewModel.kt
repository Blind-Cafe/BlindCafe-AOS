package com.abouttime.blindcafe.presentation.onboarding.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.Retrofit.JWT
import com.abouttime.blindcafe.common.constants.Retrofit.USER_ID
import com.abouttime.blindcafe.data.server.dto.login.KakaoTokenDto
import com.abouttime.blindcafe.domain.use_case.PostKakaoTokenUseCase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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


    fun postKakaoToken(kakaoTokenDto: KakaoTokenDto) = viewModelScope.launch(Dispatchers.IO) {


        postKakaoTokenUseCase(
            kakaoTokenDto
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _loginStateEvent.postValue(LoginState.Loading)
                }
                is Resource.Success -> {
                    val jwt = result.data?.jwt
                    val id = result.data?.id
                    val nick = result.data?.nickname
                    Log.d(RETROFIT_TAG, "->jwt $jwt\nid $id")
                    Log.d(RETROFIT_TAG, "-> \n${result.data?.message}\n ${result.data?.code}" )
                    if (jwt != null && id != null) {
                        saveStringData(Pair(JWT, jwt))
                        saveStringData(Pair(USER_ID, id.toString()))
                        if (nick == null) {
                            moveToAgreementFragment()
                        } else {
                            moveToMainFragment()
                        }
                        _loginStateEvent.postValue(LoginState.Success)
                    }
                }
                is Resource.Error -> {
                    Log.d(RETROFIT_TAG, result.message ?: "message null")
                    _loginStateEvent.postValue(LoginState.Error)
                }
            }
        }.launchIn(viewModelScope)


    }

    private fun moveToAgreementFragment() {
        moveToDirections(LoginFragmentDirections.actionLoginFragmentToAgreementFragment())
    }
    private fun moveToMainFragment() {
        moveToDirections(LoginFragmentDirections.actionLoginFragmentToMainFragment())
    }







}