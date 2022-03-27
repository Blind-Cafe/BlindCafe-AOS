package com.abouttime.blindcafe.presentation.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.PreferenceKey.INFO_INPUT
import com.abouttime.blindcafe.common.constants.PreferenceKey.USER_ID
import com.abouttime.blindcafe.common.constants.Retrofit.JWT
import com.abouttime.blindcafe.data.server.dto.login.KakaoTokenDto
import com.abouttime.blindcafe.domain.use_case.server.PostKakaoTokenUseCase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginViewModel(
    private val postKakaoTokenUseCase: PostKakaoTokenUseCase
) : BaseViewModel() {


    private val _loginStateEvent = MutableLiveData<LoginState>(LoginState.Uninitialized)
    val loginStateEvent: LiveData<LoginState> get() = _loginStateEvent



    fun loginWithKakao(context: Context, callback: (OAuthToken?, Throwable?) -> Unit) {
        showLoading()
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
        dismissLoading()
    }


    fun postKakaoToken(kakaoTokenDto: KakaoTokenDto) {


        postKakaoTokenUseCase(
            kakaoTokenDto
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
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
                    }
                    if (nick != null) {
                        saveStringData(Pair(INFO_INPUT, nick))
                    }

                    when(result.data?.code) {
                        "990" -> { // 로그인
                            moveToMainFragment()
                        }
                        "991" -> { // 회원가입
                            moveToNicknameFragment()
                        }
                        "992" -> { // 회원가입 (필수정보 미입력)
                            moveToNicknameFragment()
                        }
                        else -> {

                        }
                    }

                    dismissLoading()
                }
                is Resource.Error -> {
                    if (result.message == "400") {
                        showToast(R.string.toast_fail)
                    } else {
                        showToast(R.string.toast_check_internet)
                    }
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)


    }


    fun onClickPolicyButton() {
        moveToPolicyFragment()
    }
    fun onClickTermButton() {
        moveToTermFragment()
    }

    private fun moveToNicknameFragment() {
        moveToDirections(LoginFragmentDirections.actionLoginFragmentToNicknameFragment())
    }
    private fun moveToMainFragment() {
        moveToDirections(LoginFragmentDirections.actionLoginFragmentToMainFragment())
    }

    private fun moveToPolicyFragment() {
        moveToDirections(LoginFragmentDirections.actionLoginFragmentToPolicyFragment())
    }
    private fun moveToTermFragment() {
        moveToDirections(LoginFragmentDirections.actionLoginFragmentToTermFragment())
    }







}