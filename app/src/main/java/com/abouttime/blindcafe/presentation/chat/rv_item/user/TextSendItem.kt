package com.abouttime.blindcafe.presentation.chat.rv_item.user

import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.ext.millisecondToChatTime
import com.abouttime.blindcafe.common.ext.secondToChatTime
import com.abouttime.blindcafe.databinding.RvChatItemSendTextBinding
import com.abouttime.blindcafe.domain.model.Message
import com.xwray.groupie.viewbinding.BindableItem

class TextSendItem(private val message: Message) : BindableItem<RvChatItemSendTextBinding>() {
    override fun bind(viewBinding: RvChatItemSendTextBinding, position: Int) {
        viewBinding.root.tag = message.timestamp

        viewBinding.message = message
        viewBinding.tvTime.text =
            message.timestamp?.seconds?.secondToChatTime()
                ?: System.currentTimeMillis().millisecondToChatTime()
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_send_text

    override fun initializeViewBinding(view: View): RvChatItemSendTextBinding =
        RvChatItemSendTextBinding.bind(view)
}