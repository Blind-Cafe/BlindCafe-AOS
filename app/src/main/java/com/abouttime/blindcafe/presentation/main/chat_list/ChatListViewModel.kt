package com.abouttime.blindcafe.presentation.main.chat_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.data.server.dto.matching.Matching
import com.abouttime.blindcafe.domain.use_case.server.GetChatRoomsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatListViewModel(
    private val getChatRoomsUseCase: GetChatRoomsUseCase
): BaseViewModel() {



    private val _chatRooms = MutableLiveData<List<Matching>>()
    val chatRooms: LiveData<List<Matching>> get() = _chatRooms


    init {
        getChatRooms()
    }

    fun getChatRooms() {
        getChatRoomsUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    Log.e(RETROFIT_TAG, "Loading")
                }
                is Resource.Success -> {
                    Log.e(RETROFIT_TAG, result.data.toString())
                    result?.data?.matchings?.let { matches ->
                        _chatRooms.value = matches
                    }

                }
                is Resource.Error -> {

                }
            }



        }.launchIn(viewModelScope)
    }
}