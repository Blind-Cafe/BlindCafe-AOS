package com.abouttime.blindcafe.presentation.main.matching

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.CHATTING_TAG
import com.abouttime.blindcafe.common.constants.LogTag.FIRESTORE_TAG
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.use_case.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchingViewModel(
    private val receiveMessageUseCase: ReceiveMessageUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val uploadAudioUseCase: UploadAudioUseCase,
    private val downloadImageUrlUseCase: DownloadImageUrlUseCase,
    private val downloadAudioUrlUseCase: DownloadAudioUrlUseCase,
) : BaseViewModel() {

    private val _isSendButtonEnabled = MutableLiveData(false)
    val isSendButtonEnabled: LiveData<Boolean> get() = _isSendButtonEnabled


    private val _messageEditText = MutableLiveData<String>()
    val messageEditText: MutableLiveData<String> get() = _messageEditText

    private val _receivedMessage = MutableLiveData<List<Message>>()
    val receivedMessage: LiveData<List<Message>> get() = _receivedMessage

    init {
        receiveMessages("-")
    }

    fun receiveMessages(roomId: String) = viewModelScope.launch(Dispatchers.IO) {
        receiveMessageUseCase(roomId).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d(FIRESTORE_TAG, "Messages Loading")
                }
                is Resource.Success -> {
                    Log.e(FIRESTORE_TAG, "Messages ${result.data}")
                    _receivedMessage.postValue(result.data!!)
                }
                is Resource.Error -> {
                    Log.d(FIRESTORE_TAG, "Messages Loading")
                }
            }
        }
    }


    fun sendMessage(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        sendMessageUseCase(message).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d(FIRESTORE_TAG, "Loading")
                }
                is Resource.Success -> {
                    Log.d(FIRESTORE_TAG, "${result.data?.id}")
                }
                is Resource.Error -> {
                    Log.d(FIRESTORE_TAG, "Error")
                }
            }

        }.launchIn(viewModelScope)
    }

    fun uploadImage(message: Message, uri: Uri) = viewModelScope.launch(Dispatchers.IO) {
        uploadImageUseCase(message, uri).collect { result ->
            when (result) {
                is Resource.Success -> {
                    sendMessage(message)
                }
                is Resource.Error -> {
                    Log.e(CHATTING_TAG, result.message ?: "error")
                }
            }
        }
    }

    fun uploadAudio(message: Message, uri: Uri) = viewModelScope.launch(Dispatchers.IO) {
        uploadAudioUseCase(message, uri).collect { result ->
            when (result) {
                is Resource.Success -> {
                    sendMessage(message)
                }
                is Resource.Error -> {
                    Log.e(CHATTING_TAG, result.message ?: "error")
                }
            }
        }

    }

    fun downloadImageUrl(message: Message, callback: (uri: Uri?) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            downloadImageUrlUseCase(message).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        Log.e(CHATTING_TAG, "uri 오는중")
                    }
                    is Resource.Success -> {
                        Log.e(CHATTING_TAG, "uri 도착")
                        withContext(Dispatchers.Main) {
                            callback(result.data)
                        }
                    }
                    is Resource.Error -> {
                        Log.e(CHATTING_TAG, "uri 에러")
                    }
                }
            }

        }

    fun downloadAudioUrl(message: Message, callback: (uri: Uri?) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            downloadAudioUrlUseCase(message).collect { result ->
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            callback(result.data)
                        }
                    }
                    is Resource.Error -> {
                        Log.e(CHATTING_TAG, result.message ?: "error")
                    }
                }
            }
        }


    fun updateSendButton() {
        _isSendButtonEnabled.value = !messageEditText.value.isNullOrEmpty()
    }


    fun moveToMainFragment() {
        moveToDirections(MatchingFragmentDirections.actionMatchingFragmentToMainFragment())
    }

    fun moveToGalleryDialogFragment() {
        moveToDirections(MatchingFragmentDirections.actionMatchingFragmentToGalleryDialogFragment())
    }
}