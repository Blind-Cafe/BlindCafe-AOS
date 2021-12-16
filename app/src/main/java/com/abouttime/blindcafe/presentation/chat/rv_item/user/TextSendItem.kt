package com.abouttime.blindcafe.presentation.chat.rv_item.user

import android.util.Log
import android.view.View
import androidx.core.view.isGone
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.ext.millisecondToChatTime
import com.abouttime.blindcafe.common.ext.secondToChatTime
import com.abouttime.blindcafe.common.ext.setMarginTop
import com.abouttime.blindcafe.databinding.RvChatItemReceiveTextBinding
import com.abouttime.blindcafe.databinding.RvChatItemSendTextBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.xwray.groupie.viewbinding.BindableItem

class TextSendItem(
    private val message: Message,
    private val viewModel: ChatViewModel,
    private val isCont: Boolean
) : BindableItem<RvChatItemSendTextBinding>() {
    override fun bind(viewBinding: RvChatItemSendTextBinding, position: Int) {
        handleSendFirstIn1Minute(viewBinding, position)
        viewBinding.message = message


        viewBinding.tvTime.text =  message.timestamp?.seconds?.secondToChatTime() ?: System.currentTimeMillis().millisecondToChatTime()
        Log.e("zxcv", "$position : ${viewBinding.tvTime.text} 이게 왜 안나와 ${viewModel.sendLastIn1Minute[position]}" )
        viewBinding.tvTime.isGone = !viewModel.sendLastIn1Minute[position]
        if (isCont) {
            viewBinding.tvTime.setTextColor(viewBinding.tvTime.resources.getColor(R.color.main, null))
        }
    }


    private fun handleSendFirstIn1Minute(viewBinding: RvChatItemSendTextBinding, position: Int) {
        if (viewModel.sendFirstIn1Minute[position].not()) {
            viewBinding.root.setMarginTop(0)
            viewBinding.tvContentText.setBackgroundResource(R.drawable.bg_chat_send_text_round)
        } else {
            viewBinding.tvContentText.setBackgroundResource(R.drawable.bg_chat_send_text)
        }
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_send_text

    override fun initializeViewBinding(view: View): RvChatItemSendTextBinding =
        RvChatItemSendTextBinding.bind(view)
}