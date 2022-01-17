package com.abouttime.blindcafe.presentation.chat.gallery

import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.data.remote.server.dto.matching.send.PostMessageDto
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.use_case.firebase.UploadImageUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostMessageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

class GalleryViewModel(
    private val uploadImageUseCase: UploadImageUseCase,
    private val postMessageUseCase: PostMessageUseCase
): BaseViewModel() {
    private val _cursor = MutableLiveData<Cursor?> ()
    val cursor: LiveData<Cursor?> get() = _cursor

    val selectedImages = LinkedList<Uri>()
    val isSelected = mapOf<Int, Int>() // key : 인덱스, value : 몇 번째 선택된 이미지인지

    var userId: String? = null
    var matchingId: Int? = null

//    val images: LiveData<PagedList<Image?>> by lazy {
//        fetchImagesUseCase()
//    }



    fun onClickSendButton() {
        // selectedImages 에 있는 이미지 전송
        sendImageMessage(selectedImages)
    }


    fun postMessage(postMessageDto: PostMessageDto, matchingId: Int) {
        postMessageUseCase(postMessageDto = postMessageDto, matchingId = matchingId)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                    }
                    is Resource.Error -> {
                        when(result.message) {
                            "1008", "1030" -> {
                                showToast(R.string.toast_gone)
                            }
                            "1150" -> {
                                showToast(R.string.toast_not_appropriate_message)
                            }
                            else -> {
                                showToast(R.string.toast_check_internet)
                            }
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun uploadImage(message: Message, uri: Uri) = viewModelScope.launch(Dispatchers.IO) {
        uploadImageUseCase(message, uri).collect { result ->
            when (result) {
                is Resource.Success -> {
                    matchingId?.let { id ->
                        postMessage(
                            postMessageDto = PostMessageDto(
                                contents = message.contents,
                                type = message.type
                            ),
                            matchingId = id
                        )
                    }

                }
                is Resource.Error -> {
                    Log.e(LogTag.CHATTING_TAG, result.message ?: "error")
                }
            }
        }
    }

    private fun sendImageMessage(uris: List<Uri>) {
        uris.forEach { uri ->
            uri?.let {
                val id = System.currentTimeMillis().toString()
                userId?.let { userId ->
                    matchingId?.let { matchingId ->
                        uploadImage(
                            message = Message(
                                senderUid = userId,
                                contents = id,
                                roomUid = matchingId.toString(),
                                type = 2
                            ),
                            uri = it
                        )
                    }
                }

            }
        }
    }



}