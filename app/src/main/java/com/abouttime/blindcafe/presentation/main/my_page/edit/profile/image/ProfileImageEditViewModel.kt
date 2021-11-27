package com.abouttime.blindcafe.presentation.main.my_page.edit.profile.image

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.PreferenceKey.USER_ID
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.domain.use_case.server.GetProfileImageUseCase
import com.abouttime.blindcafe.domain.use_case.server.PatchProfileImageUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileImageEditViewModel(
    private val getProfileImageUseCase: GetProfileImageUseCase,
    private val patchProfileImageUseCase: PatchProfileImageUseCase
): BaseViewModel() {


    private val _imageUrls = MutableLiveData<List<String>>(listOf<String>())
    val imageUrls: LiveData<List<String>> get() = _imageUrls

    val editedUrls = mutableListOf<Uri>()


    init {
        val userId = getStringData(USER_ID)
        userId?.let {
            getProfileInfo(it.toInt())
        }

    }

    /** use cases **/
    private fun getProfileInfo(userId: Int) {
        getProfileImageUseCase(userId).onEach { result ->
            when (result) {
                is Resource.Loading -> { showLoading() }
                is Resource.Success -> {
                    result.data?.images?.toString()?.let { Log.e("observeImageUrlsData", it) }

                    _imageUrls.value = (result.data?.images ?: listOf("", "", ""))
                    dismissLoading()
                }
                is Resource.Error -> {
                    Log.e(RETROFIT_TAG, result.message.toString())
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun patchProfileImage(priority: RequestBody, image: MultipartBody.Part, callback: () -> Unit) {
        patchProfileImageUseCase(priority, image).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                   // showToast(R.string.profile_image_edit_toast_success)
                    callback()
                    dismissLoading()
                }
                is Resource.Error -> {
                    Log.e(RETROFIT_TAG, result.message.toString())
                    showToast(R.string.temp_error)
                    dismissLoading()
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