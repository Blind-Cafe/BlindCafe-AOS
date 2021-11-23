package com.abouttime.blindcafe.presentation.chat

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.CHATTING_TAG
import com.abouttime.blindcafe.common.constants.LogTag.FIRESTORE_TAG
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.Retrofit.USER_ID
import com.abouttime.blindcafe.data.server.dto.notification.PostFcmDto
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.use_case.*
import com.abouttime.blindcafe.presentation.chat.audio.RecorderState
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ChatViewModel(
    private val receiveMessageUseCase: ReceiveMessageUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val uploadAudioUseCase: UploadAudioUseCase,
    private val downloadImageUrlUseCase: DownloadImageUrlUseCase,
    private val downloadAudioUrlUseCase: DownloadAudioUrlUseCase,
    private val fcmUseCase: PostFcmUseCase,
    private val getChatRoomInfoUseCase: GetChatRoomInfoUseCase
) : BaseViewModel() {

    private val _isSendButtonEnabled = MutableLiveData(false)
    val isSendButtonEnabled: LiveData<Boolean> get() = _isSendButtonEnabled


    private val _messageEditText = MutableLiveData<String>()
    val messageEditText: MutableLiveData<String> get() = _messageEditText

    private val _receivedMessage = MutableLiveData<List<Message>>()
    val receivedMessage: LiveData<List<Message>> get() = _receivedMessage

    private val _recorderState = MutableLiveData<RecorderState>(RecorderState.BEFORE_RECORDING)
    val recorderState: LiveData<RecorderState> get() = _recorderState

    var partnerNickname: String? = null
    var startTime: String? = null
    var matchingId: Int? = null
    val userId = getStringData(USER_ID)


    init {
        Log.e(RETROFIT_TAG, "ChatViewModel init")
    }

    fun postFcm(title: String, path: String, body: String) = viewModelScope.launch(Dispatchers.IO) {
        val token = FirebaseMessaging.getInstance().token.await()
        val dto = PostFcmDto(
           body = body,
           path = path,
           targetToken = token,
           title = title
        )
        fcmUseCase(dto)
    }

    fun subscribeMessages(roomId: String) = viewModelScope.launch(Dispatchers.IO) {
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

                    }
                    is Resource.Success -> {

                        withContext(Dispatchers.Main) {
                            callback(result.data)
                        }
                    }
                    is Resource.Error -> {

                    }
                }
            }

        }

    fun downloadAudioUrl(message: Message, callback: (uri: Uri?) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            downloadAudioUrlUseCase(message).collect { result ->
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
                        Log.e(CHATTING_TAG, result.message ?: "error")
                    }
                }
            }
        }


    fun getChatRoomInfo() {
        matchingId?.let {
            getChatRoomInfoUseCase(it).onEach { result ->
                when(result) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {

                    }
                    is Resource.Error -> {}
                }

            }.launchIn(viewModelScope)
        }

    }


    fun updateSendButton() {
        _isSendButtonEnabled.value = !messageEditText.value.isNullOrEmpty()
    }

    /** onClick **/
    fun onClickRecorderButton() {
        _recorderState.value = RecorderState.START_RECORDING
    }
    fun onClickRecorderContainerButton() {
        _recorderState.value = RecorderState.STOP_RECORDING
    }



    /** move **/
    fun moveToQuitDialogFragment() {
        matchingId?.let {
            moveToDirections(ChatFragmentDirections.actionMatchingFragmentToQuitReasonDialogFragment(
                matchingId = it
            ))
        } ?: kotlin.run {
            popDirections()
        }

    }
    fun moveToReportDialogFragment() {
        matchingId?.let {
            moveToDirections(ChatFragmentDirections.actionMatchingFragmentToReportReasonDialogFragment(
                matchingId = it
            ))
        } ?: kotlin.run {
            popDirections()
        }

    }






}