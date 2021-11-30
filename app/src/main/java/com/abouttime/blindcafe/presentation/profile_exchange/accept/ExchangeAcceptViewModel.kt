package com.abouttime.blindcafe.presentation.profile_exchange.accept

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


    fun acceptMatching(matchingId: Int) {
        postAcceptMatchingUseCase(matchingId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.result?.let { accept ->
                        if (accept) {
                            /** 상대도 수락했으니 매칭성공 **/
                            moveToExchangeCompleteFragment(matchingId)
                        } else {
                            /** 상대가 거절한지 선택안한지 모르는 상태 -> 방나가기당함 & 수락대기 화면 나눠야하지만 일단 수락 대기  **/
                            _partnerProfile.value?.nickname?.let { nick ->
                                moveToExchangeWaitFragment(
                                    partnerNickname = nick,
                                    reason = "수락 여부를 선택"
                                )
                            }


//                            startTime?.let { time ->
//                                val t = time.toLong()
//                                val days = t.to3RangeDays()
//                                /** 상대가 거절했으니 매칭실패 -> 방나가기 당한 화면 **/
//                                _partnerProfile.value?.partnerNickname?.let { nick ->
//                                    moveToExitFragment(
//                                        isAttacker = false,
//                                        isReport = false,
//                                        title = "$nick 님과 ${days}일간의 추억은 즐거우셨나요?\n이제 더 좋은 추억을 쌓으러 가보죠!"
//                                    )
//                                }
//                            }

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
        /** 거절이유 화면으로 이동 **/
        matchingId?.let { id ->
            _partnerProfile?.value?.nickname?.let { nick ->
                startTime?.let { time ->
                    moveToDismissFragment(id, nick, time)
                }
            }

        }

    }

    fun onClickAcceptButton() {
        /** 30 번 api 호출 **/
        matchingId?.let {
            acceptMatching(it)
        }


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