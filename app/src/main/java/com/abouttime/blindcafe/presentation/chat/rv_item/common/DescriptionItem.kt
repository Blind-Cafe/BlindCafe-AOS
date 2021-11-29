package com.abouttime.blindcafe.presentation.chat.rv_item.common

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvChatItemDescriptionBinding
import com.abouttime.blindcafe.domain.model.Message
import com.xwray.groupie.viewbinding.BindableItem


class DescriptionItem(private val message: Message): BindableItem<RvChatItemDescriptionBinding>() {
    override fun bind(viewBinding: RvChatItemDescriptionBinding, position: Int) {
        viewBinding.root.tag = message.timestamp

        viewBinding.message = message
        if (message.contents.contains("<")) {
            val start = message.contents.indexOf("<")
            val end = message.contents.indexOf(">")
            val builder = SpannableStringBuilder(message.contents.replace("<", " ").replace(">", " "))
            builder.setSpan(ForegroundColorSpan(viewBinding.tvDescription.context.getColor(R.color.main)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewBinding.tvDescription.append(builder)
        } else {
            viewBinding.tvDescription.text = message.contents
        }
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_description

    override fun initializeViewBinding(view: View): RvChatItemDescriptionBinding =
        RvChatItemDescriptionBinding.bind(view)
}