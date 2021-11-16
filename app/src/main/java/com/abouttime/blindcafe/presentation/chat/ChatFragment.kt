package com.abouttime.blindcafe.presentation.chat

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.DeviceUtil
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentChatBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.gallery.GalleryDialogFragment
import com.abouttime.blindcafe.presentation.chat.rv_item.DescriptionItem
import com.example.chatexample.presentation.ui.chat.rv_item.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.reflect.Method

class ChatFragment : BaseFragment<ChatViewModel>(R.layout.fragment_chat) {
    private var binding: FragmentChatBinding? = null
    override val viewModel: ChatViewModel by viewModel()

    private val chatAdapter = GroupAdapter<GroupieViewHolder>()

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

        initMenuPopup(fragmentChatBinding)

        addBackPressButtonListener()

        initGalleryButton(fragmentChatBinding)
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
    private fun initMenuPopup(
        fragmentChatBinding
        : FragmentChatBinding,
    ) {
        fragmentChatBinding
            .ivMenu.setOnClickListener { v ->
                val popup = PopupMenu(requireContext(), v)
                popup.apply {
                    menuInflater.inflate(R.menu.chat_room_menu, popup.menu)

                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.menu_report -> {
                                showToast(R.string.chat_room_menu_report)
                            }
                            R.id.menu_quit -> {
                                showToast(R.string.chat_room_menu_quit)
                            }
                            R.id.menu_alarm -> {

                            }
                            else -> {

                            }
                        }
                        false
                    }


                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                } else {
                    try {
                        val fields = popup.javaClass.declaredFields
                        for (field in fields) {
                            if ("mPopup" == field.name) {
                                field.isAccessible = true
                                val menuPopupHelper = field[popup]
                                val classPopupHelper =
                                    Class.forName(menuPopupHelper.javaClass.name)
                                val setForceIcons: Method = classPopupHelper.getMethod(
                                    "setForceShowIcon",
                                    Boolean::class.javaPrimitiveType
                                )
                                setForceIcons.invoke(menuPopupHelper, true)
                                break
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
//            val inflater: MenuInflater = popup.menuInflater
//            inflater.inflate(R.menu.chat_room_menu, popup.menu)
                popup.show()
            }


    }

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

    private fun initGalleryButton(
        fragmentChatBinding
        : FragmentChatBinding,
    ) =
        with(fragmentChatBinding
        ) {
            btGallery.setOnClickListener {
                requestReadExternalStoragePermission()


                //viewModel!!.moveToGalleryDialogFragment()

                /*
                Intent(Intent.ACTION_GET_CONTENT).also {
                    it.type = "image/*"
                    startActivityForResult(it, 1)
                }
                */
                 */
            }
        }


    val getContent =
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

    private val callback =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                GalleryDialogFragment().show(requireActivity().supportFragmentManager, null)
            } else {

            }
        }

    private fun requestReadExternalStoragePermission() {
        if (DeviceUtil.hasPermission(requireContext())) {
            //GalleryDialogFragment().show(requireActivity().supportFragmentManager, null)
            getContent.launch("image/*")
        } else {
            callback.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    override fun onResume() {
        super.onResume()

    }


}