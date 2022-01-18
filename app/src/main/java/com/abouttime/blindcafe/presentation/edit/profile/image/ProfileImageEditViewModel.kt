package com.abouttime.blindcafe.presentation.edit.profile.image

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.domain.use_case.remote.server.DeleteProfileImageUseCase
import com.abouttime.blindcafe.domain.use_case.remote.server.GetMyProfileImageUseCase
import com.abouttime.blindcafe.domain.use_case.remote.server.PatchProfileImageUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileImageEditViewModel(
    private val getMyProfileImageUseCase: GetMyProfileImageUseCase,
    private val patchProfileImageUseCase: PatchProfileImageUseCase,
    private val deleteProfileImageUseCase: DeleteProfileImageUseCase,
) : BaseViewModel() {


    private val _imageUrls = MutableLiveData<List<String>>(listOf<String>())
    val imageUrls: LiveData<List<String>> get() = _imageUrls

    val editedUrls = mutableListOf<Uri>()


    init {
        getMyProfileImages()

    }

    /** use cases **/
    private fun getMyProfileImages() {
        getMyProfileImageUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.images?.toString()?.let { Log.e("observeImageUrlsData", it) }

                    _imageUrls.value = (result.data?.images ?: listOf())
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

    fun patchProfileImage(priority: RequestBody, image: MultipartBody.Part?, callback: () -> Unit) {
        patchProfileImageUseCase(priority, image).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    callback()
                    dismissLoading()
                }
                is Resource.Error -> {
                    showToast(R.string.toast_fail)
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteProfileImage(priority: Int, callback: () -> Unit) {
        deleteProfileImageUseCase(priority).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    callback()
                    dismissLoading()
                }
                is Resource.Error -> {
                    showToast(R.string.toast_fail)
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }

}