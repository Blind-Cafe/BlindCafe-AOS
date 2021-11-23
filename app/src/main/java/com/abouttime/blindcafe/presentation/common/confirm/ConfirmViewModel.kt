package com.abouttime.blindcafe.presentation.common.confirm

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY.INFO_INPUT
import com.abouttime.blindcafe.common.constants.Retrofit.JWT
import com.abouttime.blindcafe.domain.use_case.DeleteAccountUseCase
import com.abouttime.blindcafe.domain.use_case.DeleteExitChatRoomUseCase
import com.abouttime.blindcafe.domain.use_case.PostCancelMatchingUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConfirmViewModel(
    private val postCancelMatchingUseCase: PostCancelMatchingUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val exitChatRoomUseCase: DeleteExitChatRoomUseCase
): BaseViewModel() {

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
                    dismissLoading()
                    showToast(R.string.toast_fail)
                }
            }
        }.launchIn(viewModelScope)
    }




    fun exitChatRoom(matchingId: Int, reason: Int) {
        exitChatRoomUseCase(matchingId, reason).onEach { result ->
            when(result) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    moveToDirections(ConfirmDialogFragmentDirections.actionConfirmDialogFragmentToMainFragment())
                }
                is Resource.Error -> {
                    Log.e(RETROFIT_TAG, result.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }



    fun logout() {
        saveStringData(Pair(JWT, null))
        moveToDirections(ConfirmDialogFragmentDirections.actionConfirmDialogFragmentToLoginFragment())
    }

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