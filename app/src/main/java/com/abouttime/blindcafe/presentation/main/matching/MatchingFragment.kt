package com.abouttime.blindcafe.presentation.main.matching

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.addCallback
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.FragmentMainBinding
import com.abouttime.blindcafe.databinding.FragmentMatchingBinding
import com.abouttime.blindcafe.domain.model.Message
import com.example.chatexample.presentation.ui.chat.rv_item.MessageReceiveItem
import com.example.chatexample.presentation.ui.chat.rv_item.MessageSendItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.reflect.Method

class MatchingFragment: BaseFragment<MatchingViewModel>(R.layout.fragment_matching) {
    private var binding: FragmentMatchingBinding? = null
    override val viewModel: MatchingViewModel by viewModel()

    private val chatAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentMatchingBinding = FragmentMatchingBinding.bind(view)
        binding = fragmentMatchingBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel


        //requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)


        initSendButton(fragmentMatchingBinding)
        initInputEditText(fragmentMatchingBinding)
        initChatRecyclerView(fragmentMatchingBinding)
        initMenuPopup(fragmentMatchingBinding)
        initBackPressButton(fragmentMatchingBinding)


    }

    private fun initSendButton(fragmentMatchingBinding: FragmentMatchingBinding) = with(fragmentMatchingBinding) {
        btSend.setOnClickListener {
            btSend.requestFocus()
            etMessageInput.text.clear()
        }
    }

    private fun initInputEditText(fragmentMatchingBinding: FragmentMatchingBinding) = with(fragmentMatchingBinding) {

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
//        etMessageInput.setOnTouchListener { view, motionEvent ->
//            requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED)
//            false
//        }

    }


    private fun initChatRecyclerView(fragmentMatchingBinding: FragmentMatchingBinding) = with(fragmentMatchingBinding) {
        rvChatContainer.adapter = chatAdapter
        rvChatContainer.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun initMenuPopup(fragmentMatchingBinding: FragmentMatchingBinding) {
        fragmentMatchingBinding.ivMenu.setOnClickListener { v ->
            val popup = PopupMenu(requireContext(), v)
            popup.apply {
                menuInflater.inflate(R.menu.chat_room_menu, popup.menu)

                setOnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.menu_report -> {showToast(R.string.chat_room_menu_report)}
                        R.id.menu_quit -> {showToast(R.string.chat_room_menu_quit)}
                        else ->{}
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
                handleOnBackPressed()
            } else {
                this.remove()
                viewModel.moveToMainFragment()
            }
        }
    }







}