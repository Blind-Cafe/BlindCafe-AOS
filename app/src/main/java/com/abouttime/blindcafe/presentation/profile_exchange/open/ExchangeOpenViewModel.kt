package com.abouttime.blindcafe.presentation.profile_exchange.open

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.data.server.dto.user_info.edit.info.PutProfileInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.exchange.GetProfileForOpenDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.exchange.PostProfileForOpenDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.exchange.PostProfileForOpenResponse
import com.abouttime.blindcafe.domain.use_case.server.GetProfileForOpenUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostProfileForOpenUseCase
import com.abouttime.blindcafe.domain.use_case.server.PutProfileInfoUseCase
import com.abouttime.blindcafe.presentation.edit.profile.info.ProfileEditFragmentDirections
import kotlinx.coroutines.flow.onEach

class ExchangeOpenViewModel(
    private val postProfileForOpenUseCase: PostProfileForOpenUseCase,
    private val getProfileForOpenUseCase: GetProfileForOpenUseCase
): BaseViewModel() {




    private val _nickname = MutableLiveData<String>()
    val nickname: MutableLiveData<String> get() = _nickname

    private val _sex = SingleLiveData<String>()
    val sex: SingleLiveData<String> get() = _sex

    private val _age = SingleLiveData<String>()
    val age: SingleLiveData<String> get() = _age

    val _location = MutableLiveData<String>("위치")
    val location: LiveData<String> get() = _location

    private val _interests = MutableLiveData<List<String>>()
    val interests: LiveData<List<String>> get() = _interests

    private val _canEnableNext = MutableLiveData<Boolean>(false)
    val canEnableNext: LiveData<Boolean> get() = _canEnableNext

    fun setLocation(location: String) {
        Log.e("profile edit -> setLocation", "setLocation")
        _location.value = location
    }

    init {

    }



    /** use cases **/
    fun getProfileForOpen(matchingId: Int) {
        getProfileForOpenUseCase(matchingId).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result?.data?.let { dto ->
                        handleProfileData(dto)
                    }
                    dismissLoading()
                }
                is Resource.Error -> {
                    dismissLoading()
                }
            }
        }
    }
    fun handleProfileData(data: GetProfileForOpenDto) {
        if (data.fill == true) {
            moveToExchangeAcceptFragment()
        } else {

        }
    }

    fun postProfileForOpen(postProfileForOpenDto: PostProfileForOpenDto) {
        postProfileForOpenUseCase(postProfileForOpenDto).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.let { dto ->
                        handlePostResult(dto)
                    }
                    dismissLoading()
                }
                is Resource.Error -> {
                    dismissLoading()
                }
            }
        }

    }
    private fun handlePostResult(data: PostProfileForOpenResponse) {


    }



    /** onClick **/
    fun onClickCompleteButton() {
        if (canEnableNextButton()) {
            val state = _location.value?.split(" ")?.get(0)
            val region = _location.value?.split(" ")?.get(1)
            if (_nickname.value != null && state != null && region != null) {
                val dto = PostProfileForOpenDto(
                    nickname = _nickname.value!!,
                    state = state,
                    region = region
                )
                saveStringData(Pair(PreferenceKey.NICKNAME, _nickname.value))

                postProfileForOpen(dto)
            } else {
                showToast(R.string.temp_error)
            }

        } else {
            showToast(R.string.profile_edit_toast_alert_fill_all)
        }

    }

    fun onClickBackButton() {
        popDirections()
    }

    fun onClickLocationEditText() {
        moveToLocationFragment()
    }

    fun onClickProfileImageEditButton() {

    }

    private fun moveToLocationFragment() {
        moveToDirections(ExchangeOpenFragmentDirections.actionExchangeOpenFragmentToLocationFragment())
    }

    private fun moveToProfileImageEditFragment() {
        moveToDirections(ExchangeOpenFragmentDirections.actionExchangeOpenFragmentToProfileImageEditFragment())
    }

    private fun moveToExchangeAcceptFragment() {
        moveToDirections(ExchangeOpenFragmentDirections.actionExchangeOpenFragmentToExchangeAcceptFragment())
    }


    private fun isCorrectNickname() = _nickname.value?.length in 1..9
    private fun canEnableNextButton() = isCorrectNickname() && !_sex.value.isNullOrEmpty() && !_age.value.isNullOrEmpty() && !_location.value.isNullOrEmpty()
    fun updateNextButton() {
        _canEnableNext.value = canEnableNextButton()
    }


    fun onClickEditProfileButton() {

    }


}