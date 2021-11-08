package com.example.chatexample.presentation.ui.chat.rv_item

import android.util.Log
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.databinding.RvChatItemSendImageBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.main.matching.MatchingViewModel
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.xwray.groupie.viewbinding.BindableItem


class ImageSendItem(
    private val message: Message,
    private val viewModel: MatchingViewModel,
) : BindableItem<RvChatItemSendImageBinding>() {
    override fun bind(viewBinding: RvChatItemSendImageBinding, position: Int) {
        viewModel.downloadImageUrl(
            message = message,
            callback = { uri ->
                Log.e(LogTag.CHATTING_TAG, uri.toString())
                Glide.with(viewBinding.imageIvSend)
                    .load(uri)
                    .into(viewBinding.imageIvSend)
            }
        )
        viewBinding.messageSendTime.text = message.timestamp?.seconds.toString()

    }

    override fun getLayout(): Int = R.layout.rv_chat_item_send_image

    override fun initializeViewBinding(view: View): RvChatItemSendImageBinding =
        RvChatItemSendImageBinding.bind(view)
}