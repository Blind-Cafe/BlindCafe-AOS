package com.abouttime.blindcafe.presentation.chat

import android.Manifest
import android.content.Context
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.LogTag.CHATTING_TAG
import com.abouttime.blindcafe.common.constants.PreferenceKey.LAST_READ_MESSAGE
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_CURRENT_ROOM
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_FALSE
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_ROOM
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_TRUE
import com.abouttime.blindcafe.common.ext.*
import com.abouttime.blindcafe.common.util.DeviceUtil
import com.abouttime.blindcafe.common.util.KeyboardVisibility
import com.abouttime.blindcafe.data.server.dto.matching.send.PostMessageDto
import com.abouttime.blindcafe.databinding.FragmentChatBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.audio.RecorderState
import com.abouttime.blindcafe.presentation.chat.rv_item.*
import com.abouttime.blindcafe.presentation.chat.rv_item.common.*
import com.abouttime.blindcafe.presentation.chat.rv_item.user.*
import com.google.firebase.Timestamp
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.everything.android.ui.overscroll.IOverScrollState.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File


class ChatFragment : BaseFragment<ChatViewModel>(R.layout.fragment_chat) {
    private var binding: FragmentChatBinding? = null
    override val viewModel: ChatViewModel by viewModel()


    private val args: ChatFragmentArgs by navArgs()


    private val chatAdapter = GroupAdapter<GroupieViewHolder>()
    private var popupWindow: PopupWindow? = null

    var isCont = false
    private val timeStampList = mutableListOf<Timestamp>()
    private val messageList = mutableListOf<Message>()


    private var recorder: MediaRecorder? = null
    private val recordingFilePath: String by lazy {
        "${requireActivity().externalCacheDir?.absolutePath}/recording.mp3"
    }

    var isFirstPage = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentChatBinding = FragmentChatBinding.bind(view)
        binding = fragmentChatBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)


        initChatRecyclerView(fragmentChatBinding) // ?????? ?????????????????? ?????????
        initNavArgs() // NavArgs ?????? ????????? (??? ?????? ?????????!)


        observeNewMessagesData(fragmentChatBinding)
        subscribeMessages()

        observePagedMessagesData(fragmentChatBinding)
        receiveFirstPage(fragmentChatBinding)


        initSendButton(fragmentChatBinding) // ???????????? ?????????
        initInputEditText(fragmentChatBinding) // ????????? ????????? ?????? ??? ?????????
        initMenuButton(fragmentChatBinding) //?????? ???????????? ?????????
        initGalleryButton(fragmentChatBinding) // ????????? ?????? ?????????


        addBackPressButtonListener() // ???????????? ?????? ?????????
        observeRecorderState(fragmentChatBinding) // ?????? ????????? ?????????

        initTopicButton(fragmentChatBinding) // ???????????? ?????? ?????????
    }


    /** init variables  **/
    private fun initNavArgs() {
        viewModel.chatRoomInfo = args.chatRoomInfo
        viewModel.matchingId = viewModel.chatRoomInfo.matchingId
        viewModel.partnerNickname = viewModel.chatRoomInfo.nickname
        viewModel.profileImage = viewModel.chatRoomInfo.profileImage
        viewModel.drink = viewModel.chatRoomInfo.drink
        viewModel.commonInterest = viewModel.chatRoomInfo.interest
        viewModel.startTime = viewModel.chatRoomInfo.startTime
        viewModel.interest = viewModel.chatRoomInfo.interest
        viewModel.partnerId = args.partnerId

        isCont = args.chatRoomInfo.continuous
        initPartnerNciknameTextView() // ?????? ????????? ?????????
        initBackgroundColor()
        updateIconState()
        blockCurrentRoomNotification()
    }

    private fun receiveFirstPage(fragmentChatBinding: FragmentChatBinding) {
        isFirstPage = true
        viewModel?.matchingId?.let { id ->
            viewModel?.receivePagedMessages(id.toString(), Timestamp.now())
        }
    }

    private fun initPartnerNciknameTextView() {
        binding?.tvOtherName?.text = viewModel.partnerNickname
    }

    private fun initBackgroundColor() {
        if (isCont) {
            binding?.root?.setBackgroundColor(getColorByResId(R.color.matching_room_root_bg))
            binding?.clToolbarContainer?.setBackgroundColor(getColorByResId(R.color.matching_room_top_bg))
            binding?.mlInputContainer?.setBackgroundColor(getColorByResId(R.color.matching_room_bottom_bg))
        } else {
            binding?.root?.setBackgroundColor(getColorByResId(R.color.chat_room_root_bg))
            binding?.clToolbarContainer?.setBackgroundColor(getColorByResId(R.color.chat_room_top_bg))
            binding?.mlInputContainer?.setBackgroundColor(getColorByResId(R.color.chat_room_bottom_bg))
        }
    }

    private fun updateIconState() {
        if (isCont) {
            updateIconState(true,
                R.color.chat_room_icon_enabled,
                R.color.chat_room_icon_enabled,
                true,
                true)
        } else {
            val startTime = viewModel?.chatRoomInfo?.startTime?.toLong()
            startTime?.let { time ->
                if (time.isOver48Hours()) {
                    updateIconState(false,
                        R.color.chat_room_icon_enabled,
                        R.color.chat_room_icon_enabled,
                        true,
                        true)
                } else if (time.isOver24Hours()) {
                    updateIconState(false,
                        R.color.chat_room_icon_enabled,
                        R.color.chat_room_icon_disabled,
                        true,
                        false)
                } else {
                    updateIconState(false,
                        R.color.chat_room_icon_disabled,
                        R.color.chat_room_icon_disabled,
                        false,
                        false)
                }
            }
        }
    }

    private fun updateIconState(
        isGone: Boolean,
        color1: Int,
        color2: Int,
        enable1: Boolean,
        enable2: Boolean,
    ) {
        binding?.let {
            with(it) {
                ivBell.isGone = isGone
                ivGallery.setColorFilter(getColorByResId(color1))
                ivGallery.isEnabled = enable1
                ivRecord.setColorFilter(getColorByResId(color2))
                ivRecord.isEnabled = enable2

                if (enable1.not()) {
                    ivGallery.setOnClickListener {
                        showToast(R.string.chat_enable_24_hours_later)
                    }
                }
                if (enable2.not()) {
                    ivRecord.setOnClickListener {
                        showToast(R.string.chat_enable_48_hours_later)
                    }
                }
            }
        }

    }


    /** recycler view **/
    private fun initChatRecyclerView(
        fragmentChatBinding
        : FragmentChatBinding,
    ) =
        with(fragmentChatBinding
        ) {
            rvChatContainer.adapter = chatAdapter
            rvChatContainer.layoutManager = LinearLayoutManager(requireContext())

            val decor = OverScrollDecoratorHelper.setUpOverScroll(rvChatContainer,
                OverScrollDecoratorHelper.ORIENTATION_VERTICAL)



            decor.setOverScrollStateListener { decor, oldState, newState ->
                when (newState) {
                    STATE_DRAG_START_SIDE -> {
                        // ?????????????????? ??????
                        isFirstPage = false
                        viewModel?.matchingId?.let { id ->
                            if (timeStampList.isNotEmpty()) {
                                val time = timeStampList.last()
                                viewModel?.receivePagedMessages(id.toString(), time)
                            }
                        }
                    }
                }
            }



        }

    private fun scrollRvToBottomPosition(
        fragmentChatBinding
        : FragmentChatBinding,
    ) =
        with(fragmentChatBinding
        ) {
            if (chatAdapter.itemCount - 1 > 0) {
                rvChatContainer.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }

    private fun smoothScrollRvToBottomPosition(
        fragmentChatBinding
        : FragmentChatBinding,
    ) =
        with(fragmentChatBinding
        ) {
            if (chatAdapter.itemCount - 1 > 0) {
                rvChatContainer.smoothScrollToPosition(chatAdapter.itemCount - 1)
            }
        }


    /** subscribe messages **/
    private fun subscribeMessages() {
        viewModel?.matchingId?.let {
            viewModel.subscribeMessages(it.toString())
        } ?: kotlin.run {
            Log.e(CHATTING_TAG, "matchingId is null")
        }
    }

    /** handle message **/
    private fun observeNewMessagesData(fragmentChatBinding: FragmentChatBinding) {
        viewModel.receivedNewMessage.observe(viewLifecycleOwner) { messages ->
            messages.forEach { message ->
                message.timestamp?.let { tp ->
                    if (message.senderUid == viewModel.userId) {
                        addMessageToMe(message)
                    } else {
                        addMessageToPartner(message)
                    }
                }
            }
            scrollRvToBottomPosition(fragmentChatBinding)
        }
    }

    private fun observePagedMessagesData(fragmentChatBinding: FragmentChatBinding) {
        viewModel?.receivedPageMessage.observe(viewLifecycleOwner) { messages ->
            showLoading()
            binding?.let { b ->
                b.root.viewTreeObserver.addOnGlobalLayoutListener {
                    val heightDiff = b.root.rootView.height - b.root.height
                    if (heightDiff > 100  && isFirstPage) {
                        scrollRvToBottomPosition(fragmentChatBinding)
                        isFirstPage = false
                    }
                }
            }

            messages.forEach { message ->
                Log.e("message", "$message")
                message.timestamp?.let { tp ->
                    timeStampList.add(tp)
                    if (message.senderUid == viewModel.userId) {
                        addPagedMessageToMe(message)
                    } else {
                        addPagedMessageToPartner(message)
                    }
                }
            }


            dismissLoading()
        }
    }

    /** handle each message **/
    private fun addMessageToMe(message: Message) {
        viewModel.sendLastIn1Minute.add(true)
        viewModel.sendFirstIn1Minute.add(true)
        viewModel.messages.add(message)
        checkLastIn1MinuteForNewMessage(message)


        when (message.type) {
            1 -> chatAdapter.add(TextSendItem(message, viewModel = viewModel, isCont))
            2 -> chatAdapter.add(ImageSendItem(message, viewModel = viewModel, isCont))
            3 -> chatAdapter.add(AudioSendItem(message, viewModel = viewModel, isCont))
            4 -> chatAdapter.add(TextTopicItem(message))
            5 -> chatAdapter.add(ImageTopicItem(message, viewModel = viewModel))
            6 -> chatAdapter.add(AudioTopicItem(message, viewModel = viewModel))
            7 -> chatAdapter.add(DescriptionItem(message))
        }

    }

    private fun addMessageToPartner(message: Message) {
        viewModel.sendLastIn1Minute.add(true)
        viewModel.sendFirstIn1Minute.add(true)
        viewModel.messages.add(message)
        checkLastIn1MinuteForNewMessage(message)

        when (message.type) {
            1 -> chatAdapter.add(
                TextReceiveItem(
                    viewModel = viewModel,
                    message = message,
                    isCont = isCont,
                    nickName = viewModel.partnerNickname ?: "",
                    profileImage = viewModel.profileImage ?: "",
                ))
            2 -> chatAdapter.add(
                ImageReceiveItem(
                    message,
                    viewModel = viewModel,
                    isCont = isCont,
                    nickName = viewModel.partnerNickname ?: "",
                    profileImage = viewModel.profileImage ?: "",
                ))
            3 -> chatAdapter.add(
                AudioReceiveItem(
                    message,
                    viewModel = viewModel,
                    isCont = isCont,
                    nickName = viewModel.partnerNickname ?: "",
                    profileImage = viewModel.profileImage ?: "",
                ))
            4 -> chatAdapter.add(TextTopicItem(message))
            5 -> chatAdapter.add(ImageTopicItem(message, viewModel = viewModel))
            6 -> chatAdapter.add(AudioTopicItem(message, viewModel = viewModel))
            7 -> chatAdapter.add(DescriptionItem(message))
        }

    }

    private fun addPagedMessageToMe(message: Message) {
        viewModel.sendLastIn1Minute.addFirst(true)
        viewModel.sendFirstIn1Minute.addFirst(true)
        viewModel.messages.addFirst(message)
        checkLastIn1MinuteForPagedMessage(message)

        when (message.type) {
            1 -> chatAdapter.add(0, TextSendItem(message, viewModel, isCont))
            2 -> chatAdapter.add(0,
                ImageSendItem(message, viewModel = viewModel, isCont))
            3 -> chatAdapter.add(0,
                AudioSendItem(message, viewModel = viewModel, isCont))
            4 -> chatAdapter.add(0, TextTopicItem(message))
            5 -> chatAdapter.add(0, ImageTopicItem(message, viewModel = viewModel))
            6 -> chatAdapter.add(0, AudioTopicItem(message, viewModel = viewModel))
            7 -> chatAdapter.add(0, DescriptionItem(message))
            8 -> {
                chatAdapter.add(0, CongratsItem(message))
            }
        }


    }

    private fun addPagedMessageToPartner(message: Message) {
        if (message.type == 8) return

        viewModel.sendLastIn1Minute.addFirst(true)
        viewModel.sendFirstIn1Minute.addFirst(true)
        viewModel.messages.addFirst(message)
        checkLastIn1MinuteForPagedMessage(message)

        when (message.type) {
            1 -> chatAdapter.add(0, TextReceiveItem(
                viewModel = viewModel,
                message = message,
                isCont = isCont,
                nickName = viewModel.partnerNickname ?: "",
                profileImage = viewModel.profileImage ?: "",
            ))
            2 -> chatAdapter.add(0,
                ImageReceiveItem(
                    message,
                    viewModel = viewModel,
                    isCont = isCont,
                    nickName = viewModel.partnerNickname ?: "",
                    profileImage = viewModel.profileImage ?: "",
                ))
            3 -> chatAdapter.add(0,
                AudioReceiveItem(
                    message,
                    viewModel = viewModel,
                    isCont = isCont,
                    nickName = viewModel.partnerNickname ?: "",
                    profileImage = viewModel.profileImage ?: "",
                ))
            4 -> chatAdapter.add(0, TextTopicItem(message))
            5 -> chatAdapter.add(0, ImageTopicItem(message, viewModel = viewModel))
            6 -> chatAdapter.add(0, AudioTopicItem(message, viewModel = viewModel))
            7 -> chatAdapter.add(0, DescriptionItem(message))
        }
    }

    private fun checkLastIn1MinuteForNewMessage(message: Message) {
        val lastIdx = chatAdapter.itemCount - 1

        if (lastIdx < 0) return
        if (message.type !in 1..3) return
        if (viewModel.messages[lastIdx].senderUid != message.senderUid) return

        val lastMessageTime =
            viewModel.messages[lastIdx].timestamp?.seconds?.secondToChatTime()
        val newMessageTime =
            message.timestamp?.seconds?.secondToChatTime() ?: System.currentTimeMillis()
                .millisecondToChatTime()

        if (lastMessageTime != newMessageTime) return


        viewModel.sendLastIn1Minute.removeAt(lastIdx)
        viewModel.sendLastIn1Minute.add(lastIdx, false)
        chatAdapter.notifyItemChanged(lastIdx)

        viewModel.sendFirstIn1Minute.removeLast()
        viewModel.sendFirstIn1Minute.add(false)

    }

    private fun checkLastMessageIn1Minute(message: Message) {

    }

    private fun checkLastIn1MinuteForPagedMessage(message: Message) {
        if (chatAdapter.itemCount == 0) return
        if (message.type !in 1..3) return
        if (viewModel.messages[1].senderUid != message.senderUid) return


        val lastMessageTime =
            viewModel.messages[1].timestamp?.seconds?.secondToChatTime()
        val newMessageTime =
            message.timestamp?.seconds?.secondToChatTime() ?: System.currentTimeMillis()
                .millisecondToChatTime()

        if (lastMessageTime != newMessageTime) return

        viewModel.sendLastIn1Minute.removeFirst()
        viewModel.sendLastIn1Minute.addFirst(false)


        viewModel.sendFirstIn1Minute.removeFirst()
        viewModel.sendFirstIn1Minute.removeFirst()
        viewModel.sendFirstIn1Minute.addFirst(false)
        chatAdapter.notifyItemChanged(0)
        viewModel.sendFirstIn1Minute.addFirst(true)


    }



    /** Text Message **/
    private fun initSendButton(
        fragmentChatBinding
        : FragmentChatBinding,
    ) =
        with(fragmentChatBinding
        ) {
            flBtContainer.setOnClickListener {

                sendTextMessage(etMessageInput.text.toString().trim())
                btSend.requestFocus()
                etMessageInput.text.clear()
            }
        }

    private fun sendTextMessage(textMessage: String) {
        viewModel?.matchingId?.let { matchingId ->
            viewModel?.postMessage(
                PostMessageDto(
                    contents = textMessage,
                    type = 1
                ),
                matchingId = matchingId
            )
        }
    }

    /** edit text **/
    private fun initInputEditText(
        fragmentChatBinding
        : FragmentChatBinding,
    ) =
        with(fragmentChatBinding) {

            etMessageInput.setOnFocusChangeListener { view, isFocused ->
                etMessageInput.isCursorVisible = isFocused
                flBtContainer.isGone = !isFocused
                if (!isFocused) {
                    val imm: InputMethodManager = getInputManager()
                    imm.hideSoftInputFromWindow(etMessageInput.windowToken, 0)
                    mlInputContainer.transitionToStart()
                } else {
                    if (etMessageInput.text.isNotEmpty()) {
                        mlInputContainer.transitionToEnd()
                    }
                }
            }

            viewModel!!.messageEditText.observe(viewLifecycleOwner) { input ->
                if (input.isNotEmpty()) {
                    mlInputContainer.transitionToEnd()
                } else {
                    mlInputContainer.transitionToStart()
                }
                viewModel!!.updateSendButton()
            }

            KeyboardVisibility(
                window = requireActivity().window,
                onShowKeyboard = {
                    Log.e("zxcv", "show")
                    scrollRvToBottomPosition(fragmentChatBinding)
                },
                onHideKeyboard = {
                    Log.e("zxcv", "show")
                    scrollRvToBottomPosition(fragmentChatBinding)
                }
            )
        }

    /** Image Message **/
    private fun initGalleryButton(
        fragmentChatBinding
        : FragmentChatBinding,
    ) =
        with(fragmentChatBinding
        ) {
            ivGallery.setOnClickListener {
                openGalleryIfPermissionGranted()
            }
        }


    private fun openGalleryIfPermissionGranted() {
        if (DeviceUtil.hasExtrernalStoragePermission(requireContext())) {
            galleryCallback.launch("image/*")
        } else {
            galleryPermissionCallback.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private val galleryCallback =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            if (uris.size > 5) {
                showToast(R.string.toast_gallery_limit)
                return@registerForActivityResult
            }
            sendImageMessage(uris)
        }

    private val galleryPermissionCallback =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                //GalleryDialogFragment().show(requireActivity().supportFragmentManager, null)
                galleryCallback.launch("image/*")
            } else {
                showToast(R.string.chat_toast_permission)
            }
        }


    private fun sendImageMessage(uris: List<Uri>) {
        uris.forEach { uri ->
            uri?.let {
                val id = System.currentTimeMillis().toString()
                viewModel?.userId?.let { userId ->
                    viewModel?.matchingId?.let { matchingId ->
                        viewModel.uploadImage(
                            message = Message(
                                senderUid = userId,
                                contents = id,
                                type = 2
                            ),
                            uri = it
                        )
                    }
                }

            }
        }
    }


    /** Audio Message **/
    private fun observeRecorderState(fragmentChatBinding: FragmentChatBinding) =
        with(fragmentChatBinding) {
            viewModel?.recorderState?.observe(viewLifecycleOwner) { state ->
                when (state) {
                    RecorderState.BEFORE_RECORDING -> {
                        Log.e("record", "BEFORE_RECORDING")
                        initSoundVisualizerView(fragmentChatBinding)
                    }
                    RecorderState.START_RECORDING -> {
                        Log.e("record", "START_RECORDING")
                        recordIfPermissionGranted()
                    }
                    RecorderState.STOP_RECORDING -> {
                        Log.e("record", "STOP_RECORDING")
                        stopRecording()
                        sendImageMessage()
                    }
                }
            }
        }


    private fun initSoundVisualizerView(fragmentChatBinding: FragmentChatBinding) =
        with(fragmentChatBinding) {
            // ????????? maxAmplitude ??? ???????????????.
            soundVisualizer.onRequestCurrentAmplitude = {
                recorder?.maxAmplitude ?: 0
            }

        }

    private fun recordIfPermissionGranted() {
        if (DeviceUtil.hasRecordPermission(requireContext())) {
            startRecording()
        } else {
            recordPermissionsCallback.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    private val recordPermissionsCallback =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                startRecording()
            } else {
                showToast(R.string.chat_toast_permission)
            }
        }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            // ?????? ???????????? ??????????????? ????????? ??????
            // ????????? ?????? ?????? ????????? ?????? ?????? ???????????? ?????? ????????? ????????? ????????? ????????? ????????? ????????? ???????????? ?????? ??? ????????? ??????
            // ????????? ???????????? ?????? ???????????? ?????? ??????????????? ???????????? ??????????????? ??????????????? ???????????? ??? ?????? ???????????????
            // ??????????????? ?????? ????????? ?????? ????????? ???????????? ?????? ??????????????? ?????? ????????? ?????? ????????? ??? ??????????????? ?????? ????????? ?????? ?????? ????????????.
            setOutputFile(recordingFilePath)
            prepare()
        }
        recorder?.start()
        lifecycleScope.launch {
            delay(1000*60)
            showToast(R.string.chat_record_limit)
            stopRecording()
            sendImageMessage()
        }
        binding?.let {
            it.ivRecord.isClickable = false
            it.llRecorderContainer.visibility = View.VISIBLE
            it.soundVisualizer.startVisualizing(false)
            it.tvCountUp.startCountUp()
        }
    }

    private fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null

        binding?.let {
            it.ivRecord.isClickable = true
            it.llRecorderContainer.visibility = View.GONE
            it.soundVisualizer.stopVisualizing()
            it.tvCountUp.stopCountUp()
        }

    }

    private fun sendImageMessage() {
        val uri = Uri.fromFile(File(recordingFilePath))

        uri?.let {
            val id = System.currentTimeMillis().toString()
            viewModel?.matchingId?.let { matchingId ->
                viewModel?.userId?.let { userId ->
                    viewModel.uploadAudio(
                        message = Message(
                            senderUid = userId,
                            contents = id,
                            type = 3
                        ),
                        uri = it
                    )
                }
            }
        }
    }


    /** menu **/
    private fun initMenuButton(fragmentChatBinding: FragmentChatBinding) =
        with(fragmentChatBinding) {
            ivMenu.setOnClickListener {
                showMenuPopupWindow(fragmentChatBinding)
            }
        }

    private fun showMenuPopupWindow(fragmentChatBinding: FragmentChatBinding) {
        val inflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.pw_chat_menu, null)
        val root = view.findViewById<LinearLayout>(R.id.ll_menu_container)
        if (isCont) {
            root.setBackgroundResource(R.drawable.bg_matching_menu)
        }



        val time = view.findViewById<TextView>(R.id.tv_time)
        val report = view.findViewById<LinearLayout>(R.id.ll_report_container)
        val notification = view.findViewById<LinearLayout>(R.id.ll_notification_container)
        val ivNotification = view.findViewById<ImageView>(R.id.iv_notification)
        val tvNotification = view.findViewById<TextView>(R.id.tv_notification)
        val quit = view.findViewById<LinearLayout>(R.id.ll_quit_container)
        initTimeContainer(time)
        initReportContainer(report)
        initNotificationContainer(notification, ivNotification, tvNotification)
        initQuitContainer(quit)

        popupWindow = PopupWindow(view, WRAP_CONTENT, WRAP_CONTENT)

        popupWindow?.let { pw ->
            pw.isOutsideTouchable = true
            pw.isFocusable = true
            if (isCont) {
                pw.showAsDropDown(fragmentChatBinding.ivMenu)
                pw.contentView?.setMarginRight(50)
            } else {
                pw.showAsDropDown(fragmentChatBinding.ivBell)
                pw.contentView?.setMarginRight(40)
            }
            pw.contentView?.setMarginTop(4)
        }


    }

    private fun dismissPopupWindow() {
        popupWindow?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        popupWindow = null
    }

    private fun initTimeContainer(view: TextView) {
        viewModel?.startTime?.let { st ->
            view.text = st.toLong().secondToLapseForChat()
        }
    }

    private fun initReportContainer(view: View) {
        view.setOnClickListener {
            viewModel?.moveToReportDialogFragment()
            dismissPopupWindow()
        }
    }

    private fun initNotificationContainer(container: View, iv: ImageView, tv: TextView) {
        container.isVisible = isCont


        if (isCont) {
            viewModel.matchingId?.let { it ->
                if (getStringData("${it}${NOTIFICATION_ROOM}") == NOTIFICATION_FALSE) {
                    iv.setImageResource(R.drawable.ic_chat_room_notification_off)
                    tv.text = "?????? ??????"
                } else {
                    iv.setImageResource(R.drawable.ic_chat_room_notification_on)
                    tv.text = "?????? ??????"
                }
            }

            container.setOnClickListener {
                viewModel.matchingId?.let { it ->
                    if (getStringData("${it}${NOTIFICATION_ROOM}") == NOTIFICATION_FALSE) {

                        saveStringData(Pair("${it}${NOTIFICATION_ROOM}", NOTIFICATION_TRUE))
                        iv.setImageResource(R.drawable.ic_chat_room_notification_on)
                        tv.text = "?????? ??????"

                    } else {

                        saveStringData(Pair("${it}${NOTIFICATION_ROOM}", NOTIFICATION_FALSE))
                        iv.setImageResource(R.drawable.ic_chat_room_notification_off)
                        tv.text = "?????? ??????"

                    }
                }
            }
        }

    }

    private fun initQuitContainer(view: View) {
        view.setOnClickListener {
            viewModel?.moveToQuitDialogFragment()
            dismissPopupWindow()
        }
    }

    /** topic **/
    private fun initTopicButton(fragmentChatBinding: FragmentChatBinding) =
        with(fragmentChatBinding) {
            ivBell.setOnClickListener {
                viewModel?.getTopic()
            }
        }


    /** BackPressButton **/
    private fun addBackPressButtonListener() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val focusedView = requireActivity().currentFocus
            if (focusedView?.id == R.id.et_message_input) {
                focusedView.clearFocus()
            } else {
                remove()
                popOneDirections()
            }
        }
    }


    private fun blockCurrentRoomNotification() {
        viewModel.matchingId?.let { mId ->
            saveStringData(Pair("${mId}${NOTIFICATION_CURRENT_ROOM}", NOTIFICATION_FALSE))
        }
    }


    override fun onStop() {
        super.onStop()
        viewModel.matchingId?.let { mId ->
            saveStringData(Pair("${mId}${LAST_READ_MESSAGE}",
                (System.currentTimeMillis() / 1000).toString()))
            saveStringData(Pair("${mId}${NOTIFICATION_CURRENT_ROOM}", NOTIFICATION_TRUE))
        }
        viewModel?.matchingId?.let { id ->
            viewModel?.postExitLog(id)
        }
    }

    override fun onDestroy() {
        viewModel?.sendLastIn1Minute.clear()
        super.onDestroy()

    }

}