package com.example.chatexample.presentation.ui.chat.rv_item

import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvChatItemReceiveTextBinding
import com.abouttime.blindcafe.domain.model.Message
import com.xwray.groupie.viewbinding.BindableItem

class TextReceiveItem(private val message: Message): BindableItem<RvChatItemReceiveTextBinding>() {
    override fun bind(viewBinding: RvChatItemReceiveTextBinding, position: Int) {
        viewBinding.message = message
        viewBinding.messageReceiveTime.text = message.timestamp?.seconds.toString()
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_receive_text

    override fun initializeViewBinding(view: View): RvChatItemReceiveTextBinding =
        RvChatItemReceiveTextBinding.bind(view)
}