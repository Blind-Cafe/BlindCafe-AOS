package com.abouttime.blindcafe.presentation.main.my_page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
    private val _location = MutableLiveData("-")
    val location: LiveData<String> get() = _location
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
                    Log.d(RETROFIT_TAG, "getUserInfo Loading")
                }
                is Resource.Success -> {
                    Log.d(RETROFIT_TAG, "getUserInfo ${response.data.toString()}")
                    response.data?.let { dto ->
                        Log.e("mypage", dto.toString())

                        Log.e("mypage", dto.profileImage.toString())
                        dto.profileImage?.let {
                            _profileImageUrl.postValue(it)
                        }


                        Log.e("mypage", dto.nickname.toString())
                        dto.nickname?.let {
                            _nickname.postValue(it)
                        }


                        Log.e("mypage", dto.myGender.toString())
                        dto.myGender?.let {
                            _sex.postValue(it)
                        }


                        Log.e("mypage", dto.age.toString())
                        dto.age?.let {
                            _age.postValue(it.toString())
                        }

                        Log.e("mypage", dto.partnerGender.toString())
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


                        dto.region?.let {
                            _location.postValue(it)
                        }

                        dto.interests?.let {
                            if (!it.isNullOrEmpty()) {
                                _interests.postValue(it)
                            }
                        }

                        dto.drinks?.let {
                            if (!it.isNullOrEmpty()) {
                                _badges.postValue(it)
                            }
                        }

                        Log.e("mypage", dto.toString())

                    }
                }
                is Resource.Error -> {
                    Log.d(RETROFIT_TAG, "getUserInfo ${response.message}")
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