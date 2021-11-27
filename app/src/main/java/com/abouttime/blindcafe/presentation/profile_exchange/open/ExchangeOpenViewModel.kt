package com.abouttime.blindcafe.presentation.profile_exchange.open

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey
import com.abouttime.blindcafe.common.constants.PreferenceKey.NICKNAME
import com.abouttime.blindcafe.common.constants.PreferenceKey.USER_ID
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.data.server.dto.user_info.edit.info.PutProfileInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.exchange.GetProfileForOpenDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.exchange.PostProfileForOpenDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.exchange.PostProfileForOpenResponse
import com.abouttime.blindcafe.domain.use_case.server.GetProfileForOpenUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostProfileForOpenUseCase
import com.abouttime.blindcafe.domain.use_case.server.PutProfileInfoUseCase
import com.abouttime.blindcafe.presentation.edit.profile.info.ProfileEditFragmentDirections
import kotlinx.coroutines.flow.launchIn
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

    private val _profileImage = MutableLiveData<String>()
    val profileImage: LiveData<String> get() = _profileImage

    var partnerNickname: String? = null

    fun setLocation(location: String) {
        Log.e("profile edit -> setLocation", "setLocation")
        _location.value = location
    }




    /** use cases **/
    fun getProfileForOpen(matchingId: Int) {
        /** 뷰 만들어지자마자 호출됨 */
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
        }.launchIn(viewModelScope)
    }


    private fun postProfileForOpen(postProfileForOpenDto: PostProfileForOpenDto) {
        postProfileForOpenUseCase(postProfileForOpenDto).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.let { dto ->
                        handlePostResult(dto)
                    }
                    _nickname.value?.let {
                        saveStringData(Pair(NICKNAME, it))
                    }

                    dismissLoading()
                }
                is Resource.Error -> {
                    showToast(R.string.temp_error)
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)

    }

    /** handler **/
    fun handleProfileData(data: GetProfileForOpenDto) {

        _age.value = data.age?.toString()+"세" ?: ""
        _nickname.value = data.nickname ?: ""
        data.gender?.let {
            _sex.value = when(it) {
                "M" -> "남자"
                "F" -> "여자"
                else -> ""
            }
        }

        _location.value = data.region ?: ""
        data.interests.let {
            _interests.value = it
        }
        data.profileImage?.let {
            _profileImage.value = data.profileImage.toString()
        }

        partnerNickname = data.partnerNickname

        data.nickname?.let {
            saveStringData(Pair(NICKNAME, it))
        }
        data.userId?.let {
            saveStringData(Pair(USER_ID, it.toString()))
        }



        if (data.fill == true) {
            /** 바로 postProfileForOpenUseCase */
            val state = data.region?.split(" ")?.get(0)
            val region = data.region?.split(" ")?.get(1)
            if (data.nickname != null && state != null && region != null) {
                val dto = PostProfileForOpenDto(
                    nickname = _nickname.value!!,
                    state = state,
                    region = region
                )
                postProfileForOpen(dto)
            } else {
                showToast(R.string.temp_error)
            }


        } else {
            /** 작성 후 postProfileForOpenUseCase */
            showToast(R.string.profile_edit_toast_alert_fill_all)
        }
    }


    private fun handlePostResult(data: PostProfileForOpenResponse) {
        if (data.result == true) {
            /** 상대방이 작성했으니 '(내)수락' 화면으로 이동 */
            moveToExchangeAcceptFragment()
        } else {
            /** 상대방이 작성안했으니 '작성대기' 화면으로 이동 */
            data.nickname?.let {
                moveToProfileWaitFragment(it)
            }

        }

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

    fun onClickEditProfileImageButton() {
        moveToProfileImageEditFragment()
    }



    /** navigate **/
    private fun moveToLocationFragment() {
        moveToDirections(ExchangeOpenFragmentDirections.actionExchangeOpenFragmentToLocationFragment())
    }

    private fun moveToProfileImageEditFragment() {
        moveToDirections(ExchangeOpenFragmentDirections.actionExchangeOpenFragmentToProfileImageEditFragment())
    }

    private fun moveToExchangeAcceptFragment() {
        moveToDirections(ExchangeOpenFragmentDirections.actionExchangeOpenFragmentToExchangeAcceptFragment())
    }
    private fun moveToProfileWaitFragment(partnerNickname: String) {
        moveToDirections(ExchangeOpenFragmentDirections.actionExchangeOpenFragmentToExchangeWaitFragment(partnerNickname))
    }


    private fun isCorrectNickname() = _nickname.value?.length in 1..9
    private fun canEnableNextButton() = isCorrectNickname() &&  !_location.value.isNullOrEmpty()

    fun updateNextButton() {
        _canEnableNext.value = canEnableNextButton()
    }


    fun onClickEditProfileButton() {

    }


}