package com.abouttime.blindcafe.presentation.main.my_page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.domain.use_case.server.GetUserInfoUseCase
import com.abouttime.blindcafe.presentation.main.MainFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPageViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase
): BaseViewModel() {


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


    private fun getUserInfo() = viewModelScope.launch(Dispatchers.IO) {
        getUserInfoUseCase().onEach { response ->
            when(response) {
                is Resource.Loading -> {
                    Log.d(RETROFIT_TAG, "getUserInfo Loading")
                }
                is Resource.Success -> {
                    Log.d(RETROFIT_TAG, "getUserInfo ${response.data.toString()}")
                    response.data?.let {
                        Log.e("mypage", it.toString())
                        withContext(Dispatchers.Main) {
                            _profileImageUrl.value = (it.profileImage!!)
                            _nickname.value = (it.nickname)
                            _sex.value = (it.myGender)
                            _age.value = (it.age.toString())
                            _partnerSex.value = (
                                when (it.partnerGender) {
                                    "M" -> "남자"
                                    "F" -> "여자"
                                    "N" -> "상관없음"
                                    else -> ""
                                }
                            )
                            _location.value = (it.region)
                            if (!it.interests.isNullOrEmpty()) {
                                _interests.value = (it.interests!!)
                            }
                            if (!it.drinks.isNullOrEmpty()) {
                                _badges.value = (it.drinks!!)
                            }
                        }
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