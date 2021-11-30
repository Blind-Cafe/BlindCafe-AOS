package com.abouttime.blindcafe.presentation.main.my_page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.domain.use_case.server.GetUserInfoUseCase
import com.abouttime.blindcafe.presentation.main.MainFragmentDirections
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MyPageViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : BaseViewModel() {


    private val _profileImageUrl = MutableLiveData<String>("")
    val profileImageUrl: LiveData<String> get() = _profileImageUrl
    private val _nickname = MutableLiveData("-")
    val nickname: LiveData<String> get() = _nickname
    private val _sex = MutableLiveData("-")
    val sex: LiveData<String> get() = _sex
    private val _age = MutableLiveData("-")
    val age: LiveData<String> get() = _age
    private val _location = MutableLiveData<String?>()
    val location: LiveData<String?> get() = _location
    private val _partnerSex = MutableLiveData("-")
    val partnerSex: LiveData<String> get() = _partnerSex

    private val _interests = SingleLiveData<List<Int>>()
    val interests: SingleLiveData<List<Int>> get() = _interests

    private val _badges = SingleLiveData<List<Int>>()
    val badges: SingleLiveData<List<Int>> get() = _badges


    init {
        getUserInfo()
    }


    private fun getUserInfo() {
        getUserInfoUseCase().onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    response.data?.let { dto ->
                        dto.profileImage?.let {
                            _profileImageUrl.postValue(it)
                        }
                        dto.nickname?.let {
                            _nickname.postValue(it)
                        }
                        dto.myGender?.let {
                            _sex.postValue(
                                when(it) {
                                    "M" -> "남자"
                                    "F" -> "여자"
                                    else -> ""
                                }
                            )
                        }
                        dto.age?.let {
                            _age.postValue(it.toString())
                        }
                        dto.partnerGender?.let {
                            _partnerSex.postValue(
                                when (it) {
                                    "M" -> "남자"
                                    "F" -> "여자"
                                    "N" -> "상관없음"
                                    else -> ""
                                }
                            )
                        }
                        _location.postValue(dto.region)
                        dto.interests?.let {
                            if (!it.isNullOrEmpty()) {
                                _interests.postValue(it.takeLast(3))
                            }
                        }
                        dto.drinks?.let {
                            if (!it.isNullOrEmpty()) {
                                _badges.postValue(it)
                            }
                        }
                        dismissLoading()
                    }
                }
                is Resource.Error -> {
                    if (response.message == "400") {
                        showToast(R.string.toast_fail)
                    } else {
                        showToast(R.string.toast_check_internet)
                    }
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onClickEditProfileButton() {
        moveToDirections(MainFragmentDirections.actionMainFragmentToProfileEditFragment())
    }

    fun onClickSettingButton() {
        moveToDirections(MainFragmentDirections.actionMainFragmentToSettingFragment())
    }

    fun onClickEditProfileImageButton() {
        moveToDirections(MainFragmentDirections.actionMainFragmentToProfileImageEditFragment())
    }

    fun onClickEditInterestButton() {
        moveToDirections(MainFragmentDirections.actionMainFragmentToInterestEditFragment())
    }

}