package com.abouttime.blindcafe.presentation.profile_exchange.accept

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.domain.model.Profile
import com.abouttime.blindcafe.domain.use_case.server.GetChatRoomInfoUseCase
import com.abouttime.blindcafe.domain.use_case.server.GetPartnerProfileUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostAcceptMatchingUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ExchangeAcceptViewModel(
    private val getPartnerProfileUseCase: GetPartnerProfileUseCase,
    private val postAcceptMatchingUseCase: PostAcceptMatchingUseCase,
    private val getChatRoomInfoUseCase: GetChatRoomInfoUseCase
) : BaseViewModel() {

    private val _partnerProfile = MutableLiveData<Profile>()
    val partnerProfile: LiveData<Profile> get() = _partnerProfile

    var matchingId: Int? = null
    var partnerUserId: Int? = null
    var startTime: Int? = null



    /** use cases **/
    fun getPartnerProfile(matchingId: Int) {
        getPartnerProfileUseCase(matchingId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.let {
                        _partnerProfile.value = it.toProfile()
                        partnerUserId = it.userId
                    }
                    dismissLoading()
                }
                is Resource.Error -> {

                    if (result.message == "400") {
                        showToast(R.string.toast_fail)
                    } else {
                        showToast(R.string.toast_check_internet)
                    }
                    dismissLoading()
                }
            }

        }.launchIn(viewModelScope)

    }


    fun getChatRoomInfo(matchingId: Int) {
        getChatRoomInfoUseCase(matchingId).onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    startTime = result.data?.startTime?.toInt()
                    dismissLoading()
                }
                is Resource.Error -> {
                    if (result.message == "400") {
                        showToast(R.string.toast_fail)
                    } else {
                        showToast(R.string.toast_check_internet)
                    }
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun acceptMatching(matchingId: Int) {
        postAcceptMatchingUseCase(matchingId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.result?.let { accept ->
                        if (accept) {
                            /** ????????? ??????????????? ???????????? **/
                            moveToExchangeCompleteFragment(matchingId)
                        } else {
                            /** ????????? ???????????? ??????????????? ????????? ?????? -> ?????????????????? & ???????????? ?????? ?????????????????? ?????? ?????? ??????  **/
                            _partnerProfile.value?.nickname?.let { nick ->
                                moveToExchangeWaitFragment(
                                    partnerNickname = nick,
                                    reason = "?????? ????????? ??????"
                                )
                            }
                        }
                    }
                    dismissLoading()

                }
                is Resource.Error -> {
                    if (result.message == "400") {
                        showToast(R.string.toast_fail)
                    } else {
                        showToast(R.string.toast_check_internet)
                    }
                    dismissLoading()
                }
            }

        }.launchIn(viewModelScope)
    }



    /** onClick **/
    fun onClickDismissButton() {
        /** ???????????? ???????????? ?????? **/
        matchingId?.let { id ->
            _partnerProfile?.value?.nickname?.let { nick ->
                startTime?.let { time ->
                    moveToDismissFragment(id, nick, time)
                }
            }

        }

    }

    fun onClickAcceptButton() {
        /** 30 ??? api ?????? **/
        matchingId?.let {
            acceptMatching(it)
        }
    }
    fun onClickBackButton() {
        popDirections()
    }

    fun onClickProfileImage() {
        partnerUserId?.let {
            moveToAcceptImageDialogFragment(it)
        }
    }

    /** navigate **/
    private fun moveToAcceptImageDialogFragment(userId: Int) {
        moveToDirections(ExchangeAcceptFragmentDirections.actionExchangeAcceptFragmentToAcceptImageDialogFragment(
            partnerUserId = userId
        ))
    }

    private fun moveToDismissFragment(matchingId: Int, partnerNickname: String, startTime: Int) {
        moveToDirections(ExchangeAcceptFragmentDirections.exchangeAcceptFragmentToExchangeDismissFragment(
            matchingId = matchingId,
            partnerNickname = partnerNickname,
            startTime = startTime
        ))
    }

    private fun moveToExchangeCompleteFragment(matchingId: Int) {
        moveToDirections(ExchangeAcceptFragmentDirections.exchangeAcceptFragmentToExchangeCompleteFragment(
            matchingId
        ))
    }

    private fun moveToExitFragment(isAttacker: Boolean, isReport: Boolean, title: String) {
        moveToDirections(ExchangeAcceptFragmentDirections.actionExchangeAcceptFragmentToExitFragment(
            isAttacker = isAttacker,
            isReport = isReport,
            title = title
        ))
    }
    private fun moveToExchangeWaitFragment(partnerNickname: String, reason: String) {
        moveToDirections(ExchangeAcceptFragmentDirections.actionExchangeAcceptFragmentToExchangeWaitFragment(
            partnerNickname = partnerNickname,
            reason = reason
        ))
    }
}