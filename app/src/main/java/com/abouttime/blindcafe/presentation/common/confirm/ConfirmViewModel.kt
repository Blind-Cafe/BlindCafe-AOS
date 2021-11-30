package com.abouttime.blindcafe.presentation.common.confirm

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.NavigationKey.CONFIRM_MATCHING_CANCEL
import com.abouttime.blindcafe.common.constants.NavigationKey.CONFIRM_YES
import com.abouttime.blindcafe.common.constants.PreferenceKey
import com.abouttime.blindcafe.common.constants.PreferenceKey.INFO_INPUT
import com.abouttime.blindcafe.common.constants.Retrofit.JWT
import com.abouttime.blindcafe.common.ext.isOver24Hours
import com.abouttime.blindcafe.common.ext.isOver48Hours
import com.abouttime.blindcafe.common.ext.to3RangeDays
import com.abouttime.blindcafe.domain.use_case.server.DeleteAccountUseCase
import com.abouttime.blindcafe.domain.use_case.server.DeleteDismissMatchingUseCase
import com.abouttime.blindcafe.domain.use_case.server.DeleteExitChatRoomUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostCancelMatchingUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConfirmViewModel(
    private val postCancelMatchingUseCase: PostCancelMatchingUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val exitChatRoomUseCase: DeleteExitChatRoomUseCase,
    private val deleteDismissMatchingUseCase: DeleteDismissMatchingUseCase
): BaseViewModel() {
    /** from Home **/
    fun postCancelMatching() {
        postCancelMatchingUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    Log.e(RETROFIT_TAG, result.data.toString())
                    dismissLoading()
                    popDirections()
                }
                is Resource.Error -> {
                    Log.e(RETROFIT_TAG, result.message.toString())
                    if (result.message == "400") {
                        showToast(R.string.toast_fail)
                    } else {
                        Log.e("navigation", "취소하기 누름")
                        saveNavigationData(Pair(CONFIRM_MATCHING_CANCEL, CONFIRM_YES))
                        popDirections()
                    }
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }



    /** to 나가기 화면 **/
    fun exitChatRoom(matchingId: Int, reason: Int, partnerNickname: String, startTime: Int) {
        exitChatRoomUseCase(matchingId, reason).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {

                    val t = startTime.toLong()
                    val days = t.to3RangeDays()
                    moveToDirections(ConfirmDialogFragmentDirections.actionConfirmDialogFragmentToExitFragment(
                        isAttacker = true,
                        isReport = false,
                        title = "$partnerNickname 님과 ${days}일간의 추억은 즐거우셨나요?\n이제 더 좋은 추억을 쌓으러 가보죠!"
                    ))
                    dismissLoading()
                }
                is Resource.Error -> {
                    Log.e(RETROFIT_TAG, result.message.toString())
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun exitChatRoomByReport() {
        val title = "%s님의 불편함을 느낀 대화를 종료했습니다.\n아쉽지만 새로운 손님과 또 다른 추억을 쌓으러 가볼까요?".format(getStringData(PreferenceKey.NICKNAME))
        moveToDirections(ConfirmDialogFragmentDirections.actionConfirmDialogFragmentToExitFragment(
            isAttacker = true,
            isReport = true,
            title = title
        ))
    }

    fun dismissMatching(matchingId: Int, reason: Int, partnerNickname: String, startTime: Int) {
        deleteDismissMatchingUseCase(
            matchingId = matchingId,
            reason = reason
        ).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {

                    val t = startTime.toLong()
                    val days = t.to3RangeDays()
                    moveToDirections(ConfirmDialogFragmentDirections.actionConfirmDialogFragmentToExitFragment(
                        isAttacker = true,
                        isReport = false,
                        title = "$partnerNickname 님 과 ${days}일간의 추억은 즐거우셨나요?\n이제 더 좋은 추억을 쌓으러 가보죠!"
                    ))

                    dismissLoading()

                }
                is Resource.Error -> {
                    dismissLoading()
                }
            }


        }.launchIn(viewModelScope)
    }




    /** 로그아웃 **/
    fun logout() {
        saveStringData(Pair(JWT, null))
        moveToDirections(ConfirmDialogFragmentDirections.actionConfirmDialogFragmentToLoginFragment())
    }

    /** 계정 삭제 **/
    fun deleteAccount(reason: Int) {
        deleteAccountUseCase(reason).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    Log.e(RETROFIT_TAG, "Loading")
                }
                is Resource.Success -> {
                    result.data?.code?.let {
                        Log.e(RETROFIT_TAG, it)
                        saveStringData(Pair(JWT, null))
                        saveStringData(Pair(INFO_INPUT, null))
                        moveToDirections(ConfirmDialogFragmentDirections.actionConfirmDialogFragmentToAccountDeleteCompleteFragment())
                    }

                }
                is Resource.Error -> {
                    Log.e(RETROFIT_TAG, result.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }









}