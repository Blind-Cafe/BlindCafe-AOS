package com.abouttime.blindcafe.presentation.profile_exchange.complete

import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey
import com.abouttime.blindcafe.domain.model.ChatRoom
import com.abouttime.blindcafe.domain.use_case.remote.server.GetChatRoomInfoUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ExchangeCompleteViewModel(
    private val getChatRoomInfoUseCase: GetChatRoomInfoUseCase
): BaseViewModel() {

    var matchingId: Int? = null

    /** use cases **/
    private fun getChatRoomInfo(matchingId: Int) {
        getChatRoomInfoUseCase(matchingId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    dismissLoading()
                    result.data?.let { dto ->
                        saveStringData(Pair("${matchingId}${PreferenceKey.LAST_READ_MESSAGE}", null))
                        moveToChatRoomFragment(dto.toChatRoom())
                    }
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





    fun onClickNextButton() {
        matchingId?.let { id ->
            getChatRoomInfo(id)
        }
    }

    private fun moveToChatRoomFragment(chatRoom: ChatRoom) {
        moveToDirections(ExchangeCompleteFragmentDirections.actionExchangeCompleteFragmentToChatFragment(
            chatRoomInfo = chatRoom
        ))
    }
}