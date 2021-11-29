package com.abouttime.blindcafe.presentation.chat

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.CHATTING_TAG
import com.abouttime.blindcafe.common.constants.LogTag.FIRESTORE_TAG
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.PreferenceKey.NICKNAME
import com.abouttime.blindcafe.common.constants.PreferenceKey.USER_ID
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.data.server.dto.matching.send.PostMessageDto
import com.abouttime.blindcafe.data.server.dto.matching.topic.GetTopicDto
import com.abouttime.blindcafe.data.server.dto.notification.PostFcmDto
import com.abouttime.blindcafe.domain.model.ChatRoom
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.use_case.firebase.*
import com.abouttime.blindcafe.domain.use_case.server.*
import com.abouttime.blindcafe.presentation.chat.audio.RecorderState
import com.google.firebase.Timestamp
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ChatViewModel(
    private val receiveMessagesUseCase: ReceiveMessagesUseCase,
    private val subscribeMessageUseCase: SubscribeMessageUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val uploadAudioUseCase: UploadAudioUseCase,
    private val downloadImageUrlUseCase: DownloadImageUrlUseCase,
    private val downloadAudioUrlUseCase: DownloadAudioUrlUseCase,
    private val getTopicUseCase: GetTopicUseCase,
    private val postEnteringLogUseCase: PostEnteringLogUseCase,
    private val postMessageUseCase: PostMessageUseCase
) : BaseViewModel() {

    private val _isSendButtonEnabled = MutableLiveData(false)
    val isSendButtonEnabled: LiveData<Boolean> get() = _isSendButtonEnabled


    private val _messageEditText = MutableLiveData<String>()
    val messageEditText: MutableLiveData<String> get() = _messageEditText

    private val _receivedMessage = MutableLiveData<List<Message>>()
    val receivedMessage: LiveData<List<Message>> get() = _receivedMessage

    private val _receivedPageMessage = MutableLiveData<List<Message>>()
    val receivedPageMessage: LiveData<List<Message>> get() = _receivedPageMessage

    private val _recorderState = MutableLiveData<RecorderState>(RecorderState.BEFORE_RECORDING)
    val recorderState: LiveData<RecorderState> get() = _recorderState

    private val _topic = SingleLiveData<GetTopicDto>()
    val topic: SingleLiveData<GetTopicDto> get() = _topic


    var myNickname = getStringData(NICKNAME)

    /** room info **/
    lateinit var chatRoomInfo: ChatRoom
    var matchingId: Int? = null
    var partnerNickname: String? = null
    var profileImage: String? = null
    var drink: String? = null
    var commonInterest: String? = null
    var startTime: String? = null
    var interest: String? = null

    val userId = getStringData(USER_ID)


    /** use cases - read **/
    fun receivePagedMessages(roomId: String, lastTime: Timestamp) {
        receiveMessagesUseCase(roomId, lastTime).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.let { list ->
                        _receivedPageMessage.postValue(list)
                    }
                    dismissLoading()
                }
                is Resource.Error -> {
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)

    }

    fun subscribeMessages(roomId: String) = viewModelScope.launch(Dispatchers.IO) {
        subscribeMessageUseCase(roomId).collect { result ->
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

    fun downloadImageUrl(message: Message, callback: (uri: Uri?) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            downloadImageUrlUseCase(message).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {

                        withContext(Dispatchers.Main) {
                            callback(result.data)
                        }
                        dismissLoading()
                    }
                    is Resource.Error -> {
                        dismissLoading()
                    }
                }
            }

        }

    fun downloadAudioUrl(message: Message, callback: (uri: Uri?) -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            downloadAudioUrlUseCase(message).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        withContext(Dispatchers.Main) {
                            callback(result.data)
                        }
                        dismissLoading()
                    }
                    is Resource.Error -> {
                        dismissLoading()
                    }
                }
            }
        }



    /** use cases - write **/
    fun postMessage(postMessageDto: PostMessageDto, matchingId: Int) {
        postMessageUseCase(postMessageDto = postMessageDto, matchingId = matchingId)
            .onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success ->{
                        dismissLoading()
                    }
                    is Resource.Error -> {
                        dismissLoading()
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
                    Log.e(CHATTING_TAG, result.message ?: "error")
                }
            }
        }
    }

    fun uploadAudio(message: Message, uri: Uri) = viewModelScope.launch(Dispatchers.IO) {
        uploadAudioUseCase(message, uri).collect { result ->
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
                    Log.e(CHATTING_TAG, result.message ?: "error")
                }
            }
        }

    }



    fun getTopic() {
        matchingId?.let { it ->
            getTopicUseCase(it).onEach { result ->
                when (result) {
                    is Resource.Loading -> { showLoading() }
                    is Resource.Success -> { dismissLoading() }
                    is Resource.Error -> { dismissLoading() }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun postExitLog(matchingId: Int) {
        postEnteringLogUseCase(matchingId = matchingId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    dismissLoading()
                }
                is Resource.Error -> {
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }


    /** update button **/
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

    fun onClickBackButton() {
        popDirections()
    }


    /** navigate **/
    fun moveToQuitDialogFragment() {
        matchingId?.let { mId ->
            partnerNickname?.let { nick ->
                startTime?.let { time ->
                    moveToDirections(ChatFragmentDirections.actionChatFragmentToQuitReasonDialogFragment(
                        matchingId = mId,
                        partnerNickname = nick,
                        startTime = time.toInt()
                    ))

                }
            }

        } ?: kotlin.run {
            popDirections()
        }
    }

    fun moveToReportDialogFragment() {
        matchingId?.let {
            moveToDirections(ChatFragmentDirections.actionChatFragmentToReportReasonDialogFragment(
                matchingId = it
            ))
        } ?: kotlin.run {
            popDirections()
        }
    }


}