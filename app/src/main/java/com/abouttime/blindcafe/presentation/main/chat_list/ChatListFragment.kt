package com.abouttime.blindcafe.presentation.main.chat_list

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ChatListFragment: BaseFragment<ChatListViewModel>(R.layout.fragment_chat_list) {
    override val viewModel: ChatListViewModel by viewModel()
}