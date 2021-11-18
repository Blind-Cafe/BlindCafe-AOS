package com.abouttime.blindcafe.presentation.chat

import android.Manifest
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.DeviceUtil
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.ext.setMarginRight
import com.abouttime.blindcafe.common.ext.setMarginTop
import com.abouttime.blindcafe.databinding.FragmentChatBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.recorder.RecorderState
import com.abouttime.blindcafe.presentation.chat.rv_item.DescriptionItem
import com.example.chatexample.presentation.ui.chat.rv_item.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File

class ChatFragment : BaseFragment<ChatViewModel>(R.layout.fragment_chat) {
    private var binding: FragmentChatBinding? = null
    override val viewModel: ChatViewModel by viewModel()


    private val chatAdapter = GroupAdapter<GroupieViewHolder>()
    private var popupWindow: PopupWindow? = null



    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private val recordingFilePath: String by lazy {
        "${requireActivity().externalCacheDir?.absolutePath}/recording.3gp"
    }


    private val tempUserId = "-"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentChatBinding = FragmentChatBinding.bind(view)
        binding = fragmentChatBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel


        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        initChatRecyclerView(fragmentChatBinding)
        observeMessagesData()


        initSendButton(fragmentChatBinding)
        initInputEditText(fragmentChatBinding)

        initMenuButton(fragmentChatBinding)

        addBackPressButtonListener()

        initGalleryButton(fragmentChatBinding)

        observeRecorderState(fragmentChatBinding)
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

            var isScrolling = false

            root.viewTreeObserver.addOnGlobalLayoutListener {
                val heightDiff = root.rootView.height - root.height
                Log.e("asdf", heightDiff.toString())
                if (heightDiff > 100 && !isScrolling) {
                    scrollRvToLastPosition(fragmentChatBinding
                    )
                }
            }
            rvChatContainer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    Log.e("asdf", "newState $newState")
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        isScrolling = true
                    } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        isScrolling = false
                    }
                }
            }
            )


        }

    private fun scrollRvToLastPosition(
        fragmentChatBinding
        : FragmentChatBinding,
    ) =
        with(fragmentChatBinding
        ) {
            if (chatAdapter.itemCount - 1 > 0) {
                rvChatContainer.smoothScrollToPosition(chatAdapter.itemCount - 1)
            }
        }

    /** message **/
    private fun observeMessagesData() {
        viewModel.receivedMessage.observe(viewLifecycleOwner) { messages ->
            messages.forEach { message ->
                Log.e("asdf", message.toString())
                if (message.senderUid == tempUserId) {
                    addMessageToMe(message)
                } else {
                    addMessageToPartner(message)
                }
            }
        }
    }

    private fun addMessageToMe(message: Message) {
        when (message.type) {
            1 -> chatAdapter.add(TextSendItem(message))
            2 -> chatAdapter.add(ImageSendItem(message, viewModel = viewModel))
            3 -> chatAdapter.add(AudioSendItem(message, viewModel = viewModel))
            4 -> chatAdapter.add(DescriptionItem(message))
        }
    }

    private fun addMessageToPartner(message: Message) {
        when (message.type) {
            1 -> chatAdapter.add(TextReceiveItem(message))
            2 -> chatAdapter.add(ImageReceiveItem(message, viewModel = viewModel))
            3 -> chatAdapter.add(AudioReceiveItem(message, viewModel = viewModel))
            4 -> chatAdapter.add(DescriptionItem(message))
        }
    }


    /** send button **/
    private fun initSendButton(
        fragmentChatBinding
        : FragmentChatBinding,
    ) =
        with(fragmentChatBinding
        ) {
            btSend.setOnClickListener {
                viewModel?.sendMessage(Message(
                    contents = etMessageInput.text.toString(),
                    type = 1,
                    senderUid = tempUserId,
                    roomUid = tempUserId
                ))
                btSend.requestFocus()
                etMessageInput.text.clear()
            }
        }

    /** edit text **/
    private fun initInputEditText(
        fragmentChatBinding
        : FragmentChatBinding,
    ) =
        with(fragmentChatBinding
        ) {

            etMessageInput.setOnFocusChangeListener { view, isFocused ->
                if (isFocused) {
                    etMessageInput.isCursorVisible = true
                    mlInputContainer.transitionToEnd()
                } else {
                    etMessageInput.isCursorVisible = false
                    val imm: InputMethodManager = getInputManager()
                    imm.hideSoftInputFromWindow(etMessageInput.windowToken, 0)
                    mlInputContainer.transitionToStart()
                }
            }

            viewModel!!.messageEditText.observe(viewLifecycleOwner) {
                viewModel!!.updateSendButton()
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


        val time = view.findViewById<TextView>(R.id.tv_time)
        val report = view.findViewById<LinearLayout>(R.id.ll_report_container)
        val notification = view.findViewById<LinearLayout>(R.id.ll_notification_container)
        val quit = view.findViewById<LinearLayout>(R.id.ll_quit_container)
        initTimeContainer(time)
        initReportContainer(report)
        initNotificationContainer(notification)
        initQuitContainer(quit)

        popupWindow = PopupWindow(view, WRAP_CONTENT, WRAP_CONTENT)

        popupWindow?.let { pw ->
            pw.isOutsideTouchable = true
            pw.isFocusable = true
            pw.showAsDropDown(fragmentChatBinding.ivBell)
            pw.contentView?.setMarginRight(20)
            pw.contentView?.setMarginTop(20)
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

    }

    private fun initReportContainer(view: View) {
        view.setOnClickListener {
            viewModel?.moveToReportDialogFragment()
            dismissPopupWindow()
        }
    }

    private fun initNotificationContainer(view: View) {
        view.setOnClickListener {
            showToast(R.string.toast_check_internet)
        }
    }

    private fun initQuitContainer(view: View) {
        view.setOnClickListener {
            viewModel?.moveToQuitDialogFragment()
            dismissPopupWindow()
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
                popDirections()
            }

        }
    }


    /** Gallery **/
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

    val galleryCallback =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            uris.forEach { uri ->
                uri?.let {
                    val id = System.currentTimeMillis().toString()
                    viewModel.uploadImage(
                        message = Message(
                            senderUid = tempUserId,
                            contents = id,
                            roomUid = tempUserId,
                            type = 2
                        ),
                        uri = it
                    )
                }
            }

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


    /** Audio **/
    private fun observeRecorderState(fragmentChatBinding: FragmentChatBinding) = with(fragmentChatBinding) {
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
                    sendAudioMessage()
                }
            }
        }
    }


    private fun initSoundVisualizerView(fragmentChatBinding: FragmentChatBinding) = with(fragmentChatBinding) {
        // 여기서 maxAmplitude 를 전달해준다.
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

    private fun startRecording()  {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            // 따로 저장하지 않을거라서 캐시에 저장
            // 나중에 다른 곳에 저장할 때는 내부 저장소는 녹음 파일이 얼마나 커질지 모르니 충분한 공간을 제공하지 못할 수 있음에 주의
            // 그러니 여기서도 외부 저장소의 캐시 디렉토리에 접근해서 임시적으로 녹음파일을 저장하되 이 앱이 지워지거나
            // 안드로이드 기기 내에서 용량 확보할 때쯤에는 캐시 디렉토리에 있는 파일은 쉽게 날라갈 수 있기때문에 일단 거기에 쓰는 걸로 진행한다.
            setOutputFile(recordingFilePath)
            prepare()
        }
        recorder?.start()
        binding?.let {
            Log.e("record", "Start 에서 binding 이 null 인가?")
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

    private fun sendAudioMessage() {
        val uri = Uri.fromFile(File(recordingFilePath))
        val id = System.currentTimeMillis().toString()

        uri?.let {
            val id = System.currentTimeMillis().toString()
            viewModel.uploadAudio(
                message = Message(
                    senderUid = tempUserId,
                    contents = id,
                    roomUid = tempUserId,
                    type = 3
                ),
                uri = it
            )
        }
    }










}