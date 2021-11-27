package com.abouttime.blindcafe.presentation.chat.rv_item.user

import android.util.Log
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.constants.LogTag.CHATTING_TAG
import com.abouttime.blindcafe.common.ext.millisecondToChatTime
import com.abouttime.blindcafe.common.ext.secondToChatTime
import com.abouttime.blindcafe.databinding.RvChatItemReceiveImageBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.bumptech.glide.Glide
import com.xwray.groupie.viewbinding.BindableItem

class ImageReceiveItem(
    private val message: Message,
    private val viewModel: ChatViewModel
): BindableItem<RvChatItemReceiveImageBinding>() {
    override fun bind(viewBinding: RvChatItemReceiveImageBinding, position: Int) {
        viewModel.downloadImageUrl(
            message = message,
            callback = { uri ->
                Log.e(CHATTING_TAG, uri.toString())
                Glide.with(viewBinding.ivContent)
                    .load(uri)
                    .into(viewBinding.ivContent)
            }
        )
        viewBinding.tvTime.text =
            message.timestamp?.seconds?.secondToChatTime()
                ?: System.currentTimeMillis().millisecondToChatTime()
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_receive_image

    override fun initializeViewBinding(view: View): RvChatItemReceiveImageBinding =
        RvChatItemReceiveImageBinding.bind(view)
}