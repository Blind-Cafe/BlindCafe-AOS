package com.abouttime.blindcafe.presentation.chat.rv_item.common

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvChatItemCongratsBinding
import com.abouttime.blindcafe.domain.model.Message
import com.xwray.groupie.viewbinding.BindableItem

class CongratsItem(private val message: Message) : BindableItem<RvChatItemCongratsBinding>() {
    override fun bind(viewBinding: RvChatItemCongratsBinding, position: Int) {
        viewBinding.root.tag = message.timestamp
        val badge = viewBinding.tvCongrats.resources.getString(R.string.chat_get_badge).format(message.contents)

        val start = badge.indexOf("[")
        val end = badge.indexOf("]")
        val builder = SpannableStringBuilder(message.contents.replace("[", " ").replace("]", " "))
        builder.setSpan(
            ForegroundColorSpan(viewBinding.tvCongrats.context.getColor(R.color.main)),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        viewBinding.tvCongrats.append(builder)

    }

    override fun getLayout(): Int = R.layout.rv_chat_item_congrats

    override fun initializeViewBinding(view: View): RvChatItemCongratsBinding =
        RvChatItemCongratsBinding.bind(view)
}