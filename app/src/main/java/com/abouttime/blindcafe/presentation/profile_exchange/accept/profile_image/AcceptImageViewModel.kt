package com.abouttime.blindcafe.presentation.profile_exchange.accept.profile_image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.domain.use_case.server.GetProfileImageUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AcceptImageViewModel(
    private val getProfileImageUseCase: GetProfileImageUseCase,
): BaseViewModel() {

    private val _imageUrls = MutableLiveData<List<String>>()
    val imageUrls: LiveData<List<String>> get() = _imageUrls


    /** use cases **/
    fun getProfileImages(userId: Int) {
        getProfileImageUseCase(userId = userId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.images?.let {
                        _imageUrls.value = it
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
