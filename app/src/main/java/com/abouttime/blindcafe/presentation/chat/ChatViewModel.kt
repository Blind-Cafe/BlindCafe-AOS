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
import com.abouttime.blindcafe.common.constants.PreferenceKey.LAST_READ_MESSAGE
import com.abouttime.blindcafe.common.constants.PreferenceKey.NICKNAME
import com.abouttime.blindcafe.common.constants.PreferenceKey.USER_ID
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.data.server.dto.matching.send.PostMessageDto
import com.abouttime.blindcafe.data.server.dto.matching.topic.GetTopicDto
import com.abouttime.blindcafe.domain.model.ChatRoom
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.use_case.firebase.*
import com.abouttime.blindcafe.domain.use_case.server.GetTopicUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostEnteringLogUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostMessageUseCase
import com.abouttime.blindcafe.presentation.chat.audio.RecorderState
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ChatViewModel(
    private val receiveMessagesUseCase: ReceiveMessagesUseCase,
    private val subscribeMessageUseCase: SubscribeMessageUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val uploadAudioUseCase: UploadAudioUseCase,
    private val downloadImageUrlUseCase: DownloadImageUrlUseCase,
    private val downloadAudioUrlUseCase: DownloadAudioUrlUseCase,
    private val getTopicUseCase: GetTopicUseCase,
    private val postEnteringLogUseCase: PostEnteringLogUseCase,
    private val postMessageUseCase: PostMessageUseCase,
) : BaseViewModel() {

    private val _isSendButtonEnabled = MutableLiveData(false)
    val isSendButtonEnabled: LiveData<Boolean> get() = _isSendButtonEnabled


    private val _messageEditText = MutableLiveData<String>()
    val messageEditText: MutableLiveData<String> get() = _messageEditText

    private val _receivedNewMessage = MutableLiveData<List<Message>>()
    val receivedNewMessage: LiveData<List<Message>> get() = _receivedNewMessage

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


    /** check last message in a minute  **/
    val sendLastIn1Minute = LinkedList<Boolean>()
    val sendFirstIn1Minute = LinkedList<Boolean>()
    val messages = LinkedList<Message>()
    val receiveLastIn1Minute = mutableListOf<Boolean>()



    /** use cases - read **/
    fun receivePagedMessages(roomId: String, lastTime: Timestamp) {
        receiveMessagesUseCase(roomId, lastTime).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.let { list ->
                        Log.e("isScroll", "$list 도착!!")
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

                }
                is Resource.Success -> {
                    if (result.data?.isEmpty()?.not() == true) {
                        saveStringData(
                            Pair(
                                LAST_READ_MESSAGE,
                                result.data[0].timestamp?.seconds.toString()
                            )
                        )
                        _receivedNewMessage.postValue(result.data!!)
                    }


                }
                is Resource.Error -> {

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
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        dismissLoading()
                    }
                    is Resource.Error -> {
                        showToast(R.string.toast_topic_exhaust)
                        dismissLoading()
                    }
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


    /** update button **/
    fun updateSendButton() {

        _isSendButtonEnabled.value = !messageEditText.value?.trim().isNullOrEmpty()
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