package com.abouttime.blindcafe.presentation.main.matching

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentMatchingBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.main.matching.rv_item.DescriptionItem
import com.example.chatexample.presentation.ui.chat.rv_item.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.reflect.Method

class MatchingFragment : BaseFragment<MatchingViewModel>(R.layout.fragment_matching) {
    private var binding: FragmentMatchingBinding? = null
    override val viewModel: MatchingViewModel by viewModel()

    private val chatAdapter = GroupAdapter<GroupieViewHolder>()

    private val tempUserId = "-"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentMatchingBinding = FragmentMatchingBinding.bind(view)
        binding = fragmentMatchingBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)


        initSendButton(fragmentMatchingBinding)
        initInputEditText(fragmentMatchingBinding)
        initChatRecyclerView(fragmentMatchingBinding)
        observeMessagesData(fragmentMatchingBinding)
        initMenuPopup(fragmentMatchingBinding)
        initBackPressButton(fragmentMatchingBinding)
        initGalleryButton(fragmentMatchingBinding)
    }

    private fun observeMessagesData(fragmentMatchingBinding: FragmentMatchingBinding) {
        var isFirstMessages = true

        viewModel.receivedMessage.observe(viewLifecycleOwner) { messages ->
            messages.forEach { message ->
                Log.e("asdf", message.toString())
                if (message.senderUid == tempUserId) {
                    addMessageToMe(message)
                } else {
                    addMessageToPartner(message)
                }
            }


            if (isFirstMessages) {
                scrollRvToLastPosition(fragmentMatchingBinding)
                isFirstMessages = false
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


    private fun initSendButton(fragmentMatchingBinding: FragmentMatchingBinding) =
        with(fragmentMatchingBinding) {
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

    private fun initInputEditText(fragmentMatchingBinding: FragmentMatchingBinding) =
        with(fragmentMatchingBinding) {

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


    private fun initChatRecyclerView(fragmentMatchingBinding: FragmentMatchingBinding) =
        with(fragmentMatchingBinding) {
            rvChatContainer.adapter = chatAdapter
            rvChatContainer.layoutManager = LinearLayoutManager(requireContext())



            root.viewTreeObserver.addOnGlobalLayoutListener {
                val heightDiff = root.rootView.height - root.height
                Log.e("asdf", heightDiff.toString())
                if (heightDiff > 100) {
                    scrollRvToLastPosition(fragmentMatchingBinding)
                }
            }
            rvChatContainer.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    Log.e("asdf", newState.toString())

                }
            })

        }

    private fun scrollRvToLastPosition(fragmentMatchingBinding: FragmentMatchingBinding) = with(fragmentMatchingBinding) {
        if (chatAdapter.itemCount - 1 > 0) {
            rvChatContainer.scrollToPosition(chatAdapter.itemCount - 1)
        }
    }

    private fun initMenuPopup(fragmentMatchingBinding: FragmentMatchingBinding) {
        fragmentMatchingBinding.ivMenu.setOnClickListener { v ->
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

    private fun initBackPressButton(fragmentMatchingBinding: FragmentMatchingBinding) {
        requireActivity().onBackPressedDispatcher.addCallback {
            if (requireActivity().currentFocus?.id == R.id.et_message_input) {
                requireActivity().currentFocus?.clearFocus()
            } else {
                this.remove()
                viewModel.moveToMainFragment()
            }
        }
    }

    private fun initGalleryButton(fragmentMatchingBinding: FragmentMatchingBinding) =
        with(fragmentMatchingBinding) {
            btGallery.setOnClickListener {
                Intent(Intent.ACTION_GET_CONTENT).also {
                    it.type = "image/*"
                    startActivityForResult(it, 1)
                }
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val id = System.currentTimeMillis().toString()
                viewModel.uploadImage(
                    message = Message(
                        senderUid = tempUserId,
                        contents = id,
                        roomUid = tempUserId,
                        type = 2
                    ),
                    uri = uri
                )
            }
        }
    }

}