package com.abouttime.blindcafe.presentation.main.my_page

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.domain.use_case.GetUserInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MyPageViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase
): BaseViewModel() {



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
                }
                is Resource.Error -> {
                    Log.d(RETROFIT_TAG, "getUserInfo ${response.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

}