package com.abouttime.blindcafe.presentation.main.chat_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentChatListBinding
import me.everything.android.ui.overscroll.IOverScrollState
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.koin.android.viewmodel.ext.android.viewModel

class ChatListFragment: BaseFragment<ChatListViewModel>(R.layout.fragment_chat_list) {
    override val viewModel: ChatListViewModel by viewModel()
    private var binding: FragmentChatListBinding? = null
    private lateinit var chatListRvAdapter: ChatListRvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentChatListBinding = FragmentChatListBinding.bind(view)
        binding = fragmentChatListBinding

        initRecyclerView(fragmentChatListBinding)
        observeChatRoomsData()

    }
    private fun initRecyclerView(fragmentChatListBinding: FragmentChatListBinding) = with(fragmentChatListBinding) {
        chatListRvAdapter = ChatListRvAdapter(viewModel)
        rvChatRooms.apply {
            adapter = chatListRvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val decor = OverScrollDecoratorHelper.setUpOverScroll(rvChatRooms,
            OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        decor.setOverScrollStateListener { decor, oldState, newState ->
            when (newState) {
                IOverScrollState.STATE_IDLE -> {

                }
                IOverScrollState.STATE_DRAG_START_SIDE -> {
                    // 페이지네이션 코드
                    viewModel.getChatRooms()
                }
                else -> {

                }
            }
        }



    }
    private fun observeChatRoomsData() {
        viewModel.chatRooms.observe(viewLifecycleOwner) {
            binding?.rvChatRooms?.isGone = it.isEmpty()
            binding?.ivChatListBgNone?.isGone = it.isNotEmpty()
            binding?.tvSubtitleNone?.isGone = it.isNotEmpty()
            binding?.tvTitleNone?.isGone = it.isNotEmpty()

            chatListRvAdapter.submitList(it)
        }
    }
}