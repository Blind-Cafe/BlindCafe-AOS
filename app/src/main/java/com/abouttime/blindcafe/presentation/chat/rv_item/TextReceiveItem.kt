package com.abouttime.blindcafe.presentation.chat.rv_item

import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.ext.millisecondToChatTime
import com.abouttime.blindcafe.common.ext.secondToChatTime
import com.abouttime.blindcafe.databinding.RvChatItemReceiveTextBinding
import com.abouttime.blindcafe.domain.model.Message
import com.xwray.groupie.viewbinding.BindableItem

class TextReceiveItem(private val message: Message): BindableItem<RvChatItemReceiveTextBinding>() {
    override fun bind(viewBinding: RvChatItemReceiveTextBinding, position: Int) {
        viewBinding.message = message
        viewBinding.tvTime.text =
            message.timestamp?.seconds?.secondToChatTime()
            ?: System.currentTimeMillis().millisecondToChatTime()
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_receive_text

    override fun initializeViewBinding(view: View): RvChatItemReceiveTextBinding =
        RvChatItemReceiveTextBinding.bind(view)
}