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
                    dismissLoading()
                    popDirections()
                }
                is Resource.Error -> {
                    if (result.data?.code == "400") {
                        showToast(R.string.toast_fail)
                    } else {
                        saveNavigationData(Pair(CONFIRM_MATCHING_CANCEL, CONFIRM_YES))
                        popDirections()
                    }
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }



    /** to ????????? ?????? **/
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
                        title = "$partnerNickname ?????? ${days}????????? ????????? ???????????????????\n?????? ??? ?????? ????????? ????????? ?????????!"
                    ))
                    dismissLoading()
                }
                is Resource.Error -> {
                    if (result.data?.code == "400") {
                        showToast(R.string.toast_fail)
                    } else {
                        showToast(R.string.toast_check_internet)
                    }
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun exitChatRoomByReport() {
        val title = "%s?????? ???????????? ?????? ????????? ??????????????????.\n???????????? ????????? ????????? ??? ?????? ????????? ????????? ?????????????".format(getStringData(PreferenceKey.NICKNAME))
        moveToDirections(ConfirmDialogFragmentDirections.actionConfirmDialogFragmentToExitFragment(
            isAttacker = true,
            isReport = true,
            title = title
        ))
    }

    /** ????????? ?????? ?????? **/
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
                        title = "$partnerNickname ??? ??? ${days}????????? ????????? ???????????????????\n?????? ??? ?????? ????????? ????????? ?????????!"
                    ))

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




    /** ???????????? **/
    fun logout() {
        saveStringData(Pair(JWT, null))
        saveStringData(Pair(INFO_INPUT, null))
        moveToDirections(ConfirmDialogFragmentDirections.actionConfirmDialogFragmentToLoginFragment())
    }

    /** ?????? ?????? **/
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
                    if (result.message == "400") {
                        showToast(R.string.toast_fail)
                    } else {
                        showToast(R.string.toast_check_internet)
                    }
                    Log.e(RETROFIT_TAG, result.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }









}