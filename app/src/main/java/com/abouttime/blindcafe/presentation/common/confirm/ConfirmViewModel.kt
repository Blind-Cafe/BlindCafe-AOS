package com.abouttime.blindcafe.presentation.common.confirm

import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
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
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.code?.let {
                        moveToDirections(ConfirmDialogFragmentDirections.actionConfirmDialogFragmentToAccountDeleteCompleteFragment())
                    }
                    dismissLoading()
                }
                is Resource.Error -> {

                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }



}