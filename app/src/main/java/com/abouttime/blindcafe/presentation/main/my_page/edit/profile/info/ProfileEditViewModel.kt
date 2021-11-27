package com.abouttime.blindcafe.presentation.main.my_page.edit.profile.info

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.PreferenceKey.NICKNAME
import com.abouttime.blindcafe.data.server.dto.user_info.edit.info.PutProfileInfoDto
import com.abouttime.blindcafe.domain.use_case.server.GetProfileInfoUseCase
import com.abouttime.blindcafe.domain.use_case.server.PutProfileInfoUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileEditViewModel(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val putProfileInfoUseCase: PutProfileInfoUseCase
): BaseViewModel() {



    private val _nickname = MutableLiveData<String>()
    val nickname: MutableLiveData<String> get() = _nickname

    private val _sex = SingleLiveData<String>()
    val sex: SingleLiveData<String> get() = _sex

    private val _age = SingleLiveData<String>()
    val age: SingleLiveData<String> get() = _age

    val _location = MutableLiveData<String>("위치")
    val location: LiveData<String> get() = _location

    private val _selectedPartnerSex = MutableLiveData<Int>(0)
    val selectedPartnerSex: LiveData<Int> get() = _selectedPartnerSex



    private val _canEnableNext = MutableLiveData<Boolean>(false)
    val canEnableNext: LiveData<Boolean> get() = _canEnableNext

    fun setLocation(location: String) {
        Log.e("profile edit -> setLocation", "setLocation")
        _location.value = location
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
                        _selectedPartnerSex.postValue(
                            when(dto.partnerGender) {
                                "M" -> 2
                                "F" -> 1
                                "N" -> 3
                                else -> 0
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

    private fun putProfileInfo(putProfileInfoDto: PutProfileInfoDto) {
        putProfileInfoUseCase(putProfileInfoDto).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    showToast(R.string.profile_edit_toast_complete)
                    popDirections()
                    dismissLoading()
                }
                is Resource.Error -> {
                    Log.e(RETROFIT_TAG, result.message.toString())
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }



    /** onClick **/
    fun onClickCompleteButton() {
        if (canEnableNextButton()) {
            val state = _location.value?.split(" ")?.get(0)
            val region = _location.value?.split(" ")?.get(1)
            val dto = PutProfileInfoDto(
                nickname = _nickname.value!!,
                partnerGender = when(_selectedPartnerSex.value) {
                    1 -> "F"
                    2 -> "M"
                    3 -> "N"
                    else -> ""
                },
                state = state!!,
                region = region!!
            )
            saveStringData(Pair(NICKNAME, _nickname.value))

            putProfileInfo(dto)
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

    fun onClickFemaleButton() {
        _selectedPartnerSex.value = 1
        updateNextButton()
    }
    fun onClickMaleButton() {
        _selectedPartnerSex.value = 2
        updateNextButton()
    }
    fun onClickBisexualButton() {
        _selectedPartnerSex.value = 3
        updateNextButton()
    }

    private fun moveToLocationFragment() {
        moveToDirections(ProfileEditFragmentDirections.actionProfileEditFragmentToLocationFragment())
    }


    private fun isCorrectNickname() = _nickname.value?.length in 1..9
    private fun canEnableNextButton() = isCorrectNickname() && !_sex.value.isNullOrEmpty() && !_age.value.isNullOrEmpty() && !_location.value.isNullOrEmpty() && _selectedPartnerSex.value != 0
    fun updateNextButton() {
        _canEnableNext.value = canEnableNextButton()
    }


}