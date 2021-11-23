package com.abouttime.blindcafe.presentation.common.confirm

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.domain.use_case.DeleteAccountUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConfirmViewModel(
    private val deleteAccountUseCase: DeleteAccountUseCase
): BaseViewModel() {

    fun deleteAccount(reason: Int) {
        deleteAccountUseCase(reason).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    Log.e(RETROFIT_TAG, "Loading")
                }
                is Resource.Success -> {
                    result.data?.code?.let {
                        Log.e(RETROFIT_TAG, it)
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