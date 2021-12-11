package com.abouttime.blindcafe.presentation.chat.rv_item.common

import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.ext.setImageUrl
import com.abouttime.blindcafe.databinding.RvChatItemTopicImageBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.xwray.groupie.viewbinding.BindableItem

class ImageTopicItem(
    private val message: Message,
    private val viewModel: ChatViewModel
): BindableItem<RvChatItemTopicImageBinding>() {
    override fun bind(viewBinding: RvChatItemTopicImageBinding, position: Int) {

        message.contents?.let { uri ->
            viewBinding.ivContent.setImageUrl(uri)
        }
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_topic_image

    override fun initializeViewBinding(view: View): RvChatItemTopicImageBinding =
        RvChatItemTopicImageBinding.bind(view)
}