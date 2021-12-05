package com.abouttime.blindcafe.presentation.chat.rv_item.user

import android.view.View
import androidx.core.view.isGone
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.ext.millisecondToChatTime
import com.abouttime.blindcafe.common.ext.secondToChatTime
import com.abouttime.blindcafe.common.ext.setChatImage
import com.abouttime.blindcafe.databinding.RvChatItemSendImageBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.xwray.groupie.viewbinding.BindableItem


class ImageSendItem(
    private val message: Message,
    private val viewModel: ChatViewModel,
    private val lastIn1Minute: Boolean = true
) : BindableItem<RvChatItemSendImageBinding>() {
    override fun bind(viewBinding: RvChatItemSendImageBinding, position: Int) {
        viewBinding.root.tag = message.timestamp

        viewModel.downloadImageUrl(
            message = message,
            callback = { uri ->
                viewBinding.ivContent.setChatImage(uri)
            }
        )
        viewBinding.tvTime.text =  message.timestamp?.seconds?.secondToChatTime() ?: System.currentTimeMillis().millisecondToChatTime()
        viewBinding.tvTime.isGone = !lastIn1Minute

    }

    override fun getLayout(): Int = R.layout.rv_chat_item_send_image

    override fun initializeViewBinding(view: View): RvChatItemSendImageBinding =
        RvChatItemSendImageBinding.bind(view)
}