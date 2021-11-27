package com.abouttime.blindcafe.presentation.chat.rv_item.common

import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvChatItemTopicTextBinding
import com.abouttime.blindcafe.domain.model.Message
import com.xwray.groupie.viewbinding.BindableItem


class TextTopicItem(private val message: Message) : BindableItem<RvChatItemTopicTextBinding>() {
    override fun bind(viewBinding: RvChatItemTopicTextBinding, position: Int) {
        viewBinding.message = message
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_topic_text

    override fun initializeViewBinding(view: View): RvChatItemTopicTextBinding =
        RvChatItemTopicTextBinding.bind(view)
}