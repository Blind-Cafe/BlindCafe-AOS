package com.abouttime.blindcafe.presentation.chat.rv_item.user

import android.util.Log
import android.view.View
import androidx.core.view.isGone
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.ext.millisecondToChatTime
import com.abouttime.blindcafe.common.ext.secondToChatTime
import com.abouttime.blindcafe.databinding.RvChatItemSendTextBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.xwray.groupie.viewbinding.BindableItem

class TextSendItem(
    private val message: Message,
    private val viewModel: ChatViewModel
) : BindableItem<RvChatItemSendTextBinding>() {
    override fun bind(viewBinding: RvChatItemSendTextBinding, position: Int) {
        viewBinding.message = message


        viewBinding.tvTime.text =  message.timestamp?.seconds?.secondToChatTime() ?: System.currentTimeMillis().millisecondToChatTime()
        viewBinding.tvTime.isGone = !viewModel.sendLastIn1Minute[position]
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_send_text

    override fun initializeViewBinding(view: View): RvChatItemSendTextBinding =
        RvChatItemSendTextBinding.bind(view)
}