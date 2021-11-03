package com.abouttime.blindcafe.presentation.main.chat

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ChatFragment: BaseFragment<ChatViewModel>(R.layout.fragment_chat) {
    override val viewModel: ChatViewModel by viewModel()
}