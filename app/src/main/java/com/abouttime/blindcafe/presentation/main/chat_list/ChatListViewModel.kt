package com.abouttime.blindcafe.presentation.main.chat_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.data.server.dto.matching.Matching
import com.abouttime.blindcafe.domain.model.ChatRoom
import com.abouttime.blindcafe.domain.use_case.server.GetChatRoomInfoUseCase
import com.abouttime.blindcafe.domain.use_case.server.GetChatRoomsUseCase
import com.abouttime.blindcafe.presentation.main.MainFragmentDirections
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class ChatListViewModel(
    private val getChatRoomsUseCase: GetChatRoomsUseCase,
    private val getChatRoomInfoUseCase: GetChatRoomInfoUseCase,
) : BaseViewModel() {


    private val _chatRooms = MutableLiveData<List<Matching>>()
    val chatRooms: LiveData<List<Matching>> get() = _chatRooms


    init {
        getChatRooms()
    }

    fun getChatRooms() {
        getChatRoomsUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    Log.e(RETROFIT_TAG, result.data.toString())
                    result?.data?.matchings?.let { matches ->
                        _chatRooms.value = matches
                    }
                    dismissLoading()
                }
                is Resource.Error -> {
                    if (result.message != "400") {
                        showToast(R.string.toast_check_internet)
                    } else {
                        showToast(R.string.toast_fail)
                    }

                    dismissLoading()
                }
            }


        }.launchIn(viewModelScope)
    }


    fun getChatRoomInfo(matchingId: Int, partnerId: Int) {
        getChatRoomInfoUseCase(matchingId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.toChatRoom()?.let { cr ->
                        moveToChatFragment(cr, partnerId)
                    }
                    dismissLoading()
                }
                is Resource.Error -> {
                    if (result.message != "400") {
                        showToast(R.string.toast_check_internet)
                    } else {
                        showToast(R.string.toast_fail)
                    }
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun moveToChatFragment(chatRoom: ChatRoom, partnerId: Int) {
        moveToDirections(
            MainFragmentDirections.actionMainFragmentToChatFragment(
                chatRoomInfo = chatRoom,
                partnerId = partnerId
            )
        )

    }
}