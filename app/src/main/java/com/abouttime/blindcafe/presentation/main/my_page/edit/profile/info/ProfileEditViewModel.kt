package com.abouttime.blindcafe.presentation.main.my_page.edit.profile.info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.domain.use_case.server.GetProfileInfoUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileEditViewModel(
    private val getProfileInfoUseCase: GetProfileInfoUseCase
): BaseViewModel() {



    private val _nickname = SingleLiveData<String>()
    val nickname: SingleLiveData<String> get() = _nickname

    private val _sex = SingleLiveData<String>()
    val sex: SingleLiveData<String> get() = _sex

    private val _age = SingleLiveData<String>()
    val age: SingleLiveData<String> get() = _age

    val _location = MutableLiveData<String>("위치")
    val location: LiveData<String> get() = _location


    private val _canEnableNext = MutableLiveData<Boolean>()
    val canEnableNext: LiveData<Boolean> get() = _canEnableNext

    fun setLocation(location: String) {
        _location.value = location
        _canEnableNext.value = true
    }


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
                        _nickname.postValue(dto.nickname ?: "")
                        _age.postValue(dto.age?.toString() ?: "")
                        _sex.postValue(
                            if (dto.myGender == "M") {
                                "남성"
                            } else if (dto.myGender == "F") {
                                "여성"
                            } else {
                                ""
                            }
                        )
                        _location.postValue(dto.region ?: "")
                        _canEnableNext.postValue(!dto.region.isNullOrEmpty())
                    }
                }
                is Resource.Error -> {
                    Log.e(RETROFIT_TAG, result.message.toString())
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

    fun onClickLocationEditText() {
        moveToLocationFragment()
    }

    private fun moveToLocationFragment() {
        moveToDirections(ProfileEditFragmentDirections.actionProfileEditFragmentToLocationFragment())
    }

}