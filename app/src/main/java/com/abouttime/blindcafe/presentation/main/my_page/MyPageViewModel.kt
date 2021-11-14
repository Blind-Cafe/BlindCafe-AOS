package com.abouttime.blindcafe.presentation.main.my_page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.domain.use_case.GetUserInfoUseCase
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase
): BaseViewModel() {


    private val _profileImageUrl = SingleLiveData<String>()
    val profileImageUrl: SingleLiveData<String> get() = _profileImageUrl
    private val _nickname = MutableLiveData("-")
    val nickname: LiveData<String> get() = _nickname
    private val _sex = MutableLiveData("-")
    val sex: LiveData<String> get() = _sex
    private val _age = MutableLiveData("-")
    val age: LiveData<String> get() = _age
    private val _location = MutableLiveData("-")
    val location: LiveData<String> get() = _location

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
                        _nickname.postValue(it.nickname)
                        _sex.postValue(it.myGender)
                        _age.postValue(it.age.toString())
                        _location.postValue(it.region)
                        if (!it.interests.isNullOrEmpty()) {
                            _interests.postValue(it.interests!!)
                        }
                        if (!it.drinks.isNullOrEmpty()) {
                            _badges.postValue(it.drinks!!)
                        }

                    }
                }
                is Resource.Error -> {
                    Log.d(RETROFIT_TAG, "getUserInfo ${response.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

}