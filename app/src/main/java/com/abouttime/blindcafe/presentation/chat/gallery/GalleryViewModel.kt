package com.abouttime.blindcafe.presentation.chat.gallery

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.data.local.media_store.Image
import com.abouttime.blindcafe.data.remote.server.dto.matching.send.PostMessageDto
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.use_case.local.media_store.GetGalleryImagesUseCase
import com.abouttime.blindcafe.domain.use_case.remote.firebase.UploadImageUseCase
import com.abouttime.blindcafe.domain.use_case.remote.server.PostMessageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GalleryViewModel(
    private val uploadImageUseCase: UploadImageUseCase,
    private val postMessageUseCase: PostMessageUseCase,
    private val getGalleryImagesUseCase: GetGalleryImagesUseCase,
) : BaseViewModel() {

    var dismissCallback: () -> Unit = {  }
    val imageSelector: Selector<Image> = Selector<Image>(5)

    var userId: String? = null
    var matchingId: Int? = null

    val images: LiveData<PagedList<Image?>> by lazy {
        getGalleryImagesUseCase()
    }


    fun onClickSendButton() {
        sendImageMessage(imageSelector.getItems().map { it.uri })
    }

    private fun sendImageMessage(uris: List<Uri>) {

        uris.forEach { uri ->
            val id = System.currentTimeMillis().toString()
            Log.d("asdf", "sendImageMessage $userId $matchingId")
            userId?.let { userId ->
                matchingId?.let { matchingId ->
                    uploadImage(
                        message = Message(
                            senderUid = userId,
                            contents = id,
                            roomUid = matchingId.toString(),
                            type = 2
                        ),
                        uri = uri
                    )
                }
            }
        }
    }

    private fun uploadImage(message: Message, uri: Uri) = viewModelScope.launch(Dispatchers.IO) {
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



    fun postMessage(postMessageDto: PostMessageDto, matchingId: Int) {
        postMessageUseCase(postMessageDto = postMessageDto, matchingId = matchingId)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        dismissCallback()
                    }
                    is Resource.Error -> {
                        when (result.message) {
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




}