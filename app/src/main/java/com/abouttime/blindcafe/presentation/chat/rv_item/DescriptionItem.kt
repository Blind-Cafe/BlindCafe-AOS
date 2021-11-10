package com.abouttime.blindcafe.presentation.chat.rv_item

import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvChatItemDescriptionBinding
import com.abouttime.blindcafe.domain.model.Message
import com.xwray.groupie.viewbinding.BindableItem


class DescriptionItem(private val message: Message): BindableItem<RvChatItemDescriptionBinding>() {
    override fun bind(viewBinding: RvChatItemDescriptionBinding, position: Int) {
        //viewBinding.message = message
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_description

    override fun initializeViewBinding(view: View): RvChatItemDescriptionBinding =
        RvChatItemDescriptionBinding.bind(view)
}