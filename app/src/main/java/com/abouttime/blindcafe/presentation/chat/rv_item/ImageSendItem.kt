package com.abouttime.blindcafe.presentation.chat.rv_item

import android.util.Log
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.common.ext.millisecondToChatTime
import com.abouttime.blindcafe.common.ext.secondToChatTime
import com.abouttime.blindcafe.databinding.RvChatItemSendImageBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.bumptech.glide.Glide
import com.xwray.groupie.viewbinding.BindableItem


class ImageSendItem(
    private val message: Message,
    private val viewModel: ChatViewModel,
) : BindableItem<RvChatItemSendImageBinding>() {
    override fun bind(viewBinding: RvChatItemSendImageBinding, position: Int) {
        viewModel.downloadImageUrl(
            message = message,
            callback = { uri ->
                Log.e(LogTag.CHATTING_TAG, uri.toString())
                Glide.with(viewBinding.ivContent)
                    .load(uri)
                    .into(viewBinding.ivContent)
            }
        )
        viewBinding.tvTime.text =
            message.timestamp?.seconds?.secondToChatTime()
                ?: System.currentTimeMillis().millisecondToChatTime()

    }

    override fun getLayout(): Int = R.layout.rv_chat_item_send_image

    override fun initializeViewBinding(view: View): RvChatItemSendImageBinding =
        RvChatItemSendImageBinding.bind(view)
}