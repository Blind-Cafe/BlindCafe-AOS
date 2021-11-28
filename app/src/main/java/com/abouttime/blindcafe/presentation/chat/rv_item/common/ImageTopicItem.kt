package com.abouttime.blindcafe.presentation.chat.rv_item.common

import android.util.Log
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.databinding.RvChatItemTopicImageBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.bumptech.glide.Glide
import com.xwray.groupie.viewbinding.BindableItem

class ImageTopicItem(
    private val message: Message,
    private val viewModel: ChatViewModel
): BindableItem<RvChatItemTopicImageBinding>() {
    override fun bind(viewBinding: RvChatItemTopicImageBinding, position: Int) {
        viewBinding.root.tag = message.timestamp
        message.contents?.let { uri ->
            Log.e(LogTag.CHATTING_TAG, uri.toString())
            Glide.with(viewBinding.ivContent)
                .load(uri)
                .into(viewBinding.ivContent)
        }
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_topic_image

    override fun initializeViewBinding(view: View): RvChatItemTopicImageBinding =
        RvChatItemTopicImageBinding.bind(view)
}