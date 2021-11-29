package com.abouttime.blindcafe.presentation.profile_exchange.complete

import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.domain.model.ChatRoom
import com.abouttime.blindcafe.domain.use_case.server.GetChatRoomInfoUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ExchangeCompleteViewModel(
    private val getChatRoomInfoUseCase: GetChatRoomInfoUseCase
): BaseViewModel() {

    var matchingId: Int? = null

    /** use cases **/
    fun getChatRoomInfo(matchingId: Int) {
        getChatRoomInfoUseCase(matchingId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    dismissLoading()
                    result.data?.let { dto ->
                        moveToChatRoomFragment(dto.toChatRoom())
                    }
                }
                is Resource.Error -> {
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