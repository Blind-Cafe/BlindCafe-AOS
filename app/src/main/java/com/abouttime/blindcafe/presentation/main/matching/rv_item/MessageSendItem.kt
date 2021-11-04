package com.example.chatexample.presentation.ui.chat.rv_item

import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvChatItemSendTextBinding
import com.abouttime.blindcafe.domain.model.Message
import com.xwray.groupie.viewbinding.BindableItem

class MessageSendItem(private val message: Message): BindableItem<RvChatItemSendTextBinding>() {
    override fun bind(viewBinding: RvChatItemSendTextBinding, position: Int) {
        viewBinding.message = message
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_send_text

    override fun initializeViewBinding(view: View): RvChatItemSendTextBinding =
        RvChatItemSendTextBinding.bind(view)
}