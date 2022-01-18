package com.abouttime.blindcafe.presentation.chat.partner_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.data.remote.server.dto.user_info.partner.matched.GetMatchingProfileDto
import com.abouttime.blindcafe.domain.use_case.remote.server.GetMatchingProfileUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PartnerProfileViewModel(
    private val getMatchingProfileUseCase: GetMatchingProfileUseCase
): BaseViewModel() {

    private val _imageUrls = MutableLiveData<List<String>>(listOf<String>())
    val imageUrls: LiveData<List<String>> get() = _imageUrls

    private val _nickname = MutableLiveData<String>("")
    val nickname: LiveData<String> get() = _nickname

    private val _age = MutableLiveData<String>("")
    val age: LiveData<String> get() = _age

    private val _location = MutableLiveData<String>("")
    val location: LiveData<String> get() = _location

    private val _interest1 = MutableLiveData<String>("")
    val interest1: LiveData<String> get() = _interest1
    private val _interest2 = MutableLiveData<String>("")
    val interest2: LiveData<String> get() = _interest2
    private val _interest3 = MutableLiveData<String>("")
    val interest3: LiveData<String> get() = _interest3

    /** use cases **/
    fun getMatchingProfile(userId: Int) {
        getMatchingProfileUseCase(userId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.let { dto ->
                        handleMatchingProfileDto(dto)
                    }
                   dismissLoading()
                }
                is Resource.Error -> {
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun handleMatchingProfileDto(dto: GetMatchingProfileDto) {
        _nickname.postValue(dto.nickname ?: "")
        _age.postValue(dto.age?.toString()+"살" ?: "")
        _location.postValue(dto.region ?: "")
        _interest1.postValue(dto.interests?.get(0) ?: "")
        _interest2.postValue(dto.interests?.get(1) ?: "")
        _interest3.postValue(dto.interests?.get(2) ?: "")
        _imageUrls.postValue(dto.images ?: listOf<String>())
    }


    fun onClickBackButton() {
        popDirections()
    }
}