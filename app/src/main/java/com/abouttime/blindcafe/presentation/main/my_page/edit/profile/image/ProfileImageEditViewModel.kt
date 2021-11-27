package com.abouttime.blindcafe.presentation.main.my_page.edit.profile.image

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.domain.use_case.server.GetProfileInfoUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileImageEditViewModel(
    private val getProfileInfoUseCase: GetProfileInfoUseCase
): BaseViewModel() {


    private val _imageUrls = SingleLiveData<List<String>>()
    val imageUrls: SingleLiveData<List<String>> get() = _imageUrls

    val editedUrls = mutableListOf<Uri>()


    init {
        getProfileInfo()
    }

    /** use cases **/
    private fun getProfileInfo() {
        getProfileInfoUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> { showLoading() }
                is Resource.Success -> {
                    result.data?.let { dto ->
                        _imageUrls.postValue(dto.images ?: listOf("", "", ""))
                    }
                }
                is Resource.Error -> {
                    Log.e(LogTag.RETROFIT_TAG, result.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }


    /** onClick **/
    fun onClickCompleteButton() {
        // TODO 프로필 수정 api 연결
        popDirections()
    }
    fun onClickBackButton() {
        popDirections()
    }
}