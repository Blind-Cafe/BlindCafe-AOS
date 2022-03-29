package com.abouttime.blindcafe.presentation.chat

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey.NICKNAME
import com.abouttime.blindcafe.common.constants.PreferenceKey.USER_ID
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.data.server.dto.matching.send.PostMessageDto
import com.abouttime.blindcafe.data.server.dto.matching.topic.GetTopicDto
import com.abouttime.blindcafe.domain.model.ChatRoom
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.use_case.server.GetTopicUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostEnteringLogUseCase
import com.abouttime.blindcafe.presentation.chat.audio.RecorderState
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

class ChatViewModel(
    private val getTopicUseCase: GetTopicUseCase,
    private val postEnteringLogUseCase: PostEnteringLogUseCase,
) : BaseViewModel() {

    private val _isSendButtonEnabled = MutableLiveData(false)
    val isSendButtonEnabled: LiveData<Boolean> get() = _isSendButtonEnabled


    private val _messageEditText = MutableLiveData<String>()
    val messageEditText: MutableLiveData<String> get() = _messageEditText

    private val _recorderState = MutableLiveData<RecorderState>(RecorderState.BEFORE_RECORDING)
    val recorderState: LiveData<RecorderState> get() = _recorderState


    private val _receivedNewMessage = SingleLiveData<List<Message>>()
    val receivedNewMessage: SingleLiveData<List<Message>> get() = _receivedNewMessage

    private val _receivedPageMessage = SingleLiveData<List<Message>>()
    val receivedPageMessage: SingleLiveData<List<Message>> get() = _receivedPageMessage

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
    var partnerId: Int = 0

    val userId = getStringData(USER_ID)


    /** check last message in a minute  **/
    val sendLastIn1Minute = LinkedList<Boolean>()
    val sendFirstIn1Minute = LinkedList<Boolean>()
    val messages = LinkedList<Message>()
    val receiveLastIn1Minute = mutableListOf<Boolean>()


    /** use cases - read **/
    fun receivePagedMessages(roomId: String, lastTime: Timestamp) {}

    fun subscribeMessages(roomId: String) {}

    fun downloadImageUrl(message: Message, callback: (uri: Uri?) -> Unit) {}

    fun downloadAudioUrl(message: Message, callback: (uri: Uri?) -> Unit) {}


    /** use cases - write **/
    fun postMessage(postMessageDto: PostMessageDto, matchingId: Int) {}

    fun uploadImage(message: Message, uri: Uri) {}

    fun uploadAudio(message: Message, uri: Uri) {}


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

    fun moveToPartnerProfileFragment() {
        if (partnerId != 0) {
            moveToDirections(ChatFragmentDirections.actionChatFragmentToPartnerProfileFragment(
                partnerUserId = partnerId
            ))
        }
    }

    fun moveToChatImageFragment(imageUrl: String, nick: String, date: String) {
        moveToDirections(ChatFragmentDirections.actionChatFragmentToChatImageFragment(
            imageUrl = imageUrl,
            nickname = nick,
            date = date
        ))
    }


}