package com.abouttime.blindcafe.presentation.chat.rv_item.common

import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvChatItemDateBinding
import com.abouttime.blindcafe.domain.model.Message
import com.xwray.groupie.viewbinding.BindableItem


class DateItem(private val message: Message): BindableItem<RvChatItemDateBinding>() {
    override fun bind(viewBinding: RvChatItemDateBinding, position: Int) {
        viewBinding.tvDate.text = message.contents
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_date

    override fun initializeViewBinding(view: View): RvChatItemDateBinding =
        RvChatItemDateBinding.bind(view)
}