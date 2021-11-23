package com.abouttime.blindcafe.presentation.main.chat_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentChatListBinding
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
    }
    private fun observeChatRoomsData() {
        viewModel.chatRooms.observe(viewLifecycleOwner) {
            binding?.rvChatRooms?.isGone = it.isEmpty()
            binding?.tvSubtitleNone?.isGone = it.isNotEmpty()
            binding?.tvTitleNone?.isGone = it.isNotEmpty()

            chatListRvAdapter.submitList(it)
        }
    }
}