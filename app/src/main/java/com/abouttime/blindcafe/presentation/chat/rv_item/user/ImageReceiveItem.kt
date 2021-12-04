package com.abouttime.blindcafe.presentation.chat.rv_item.user

import android.util.Log
import android.view.View
import androidx.core.view.isGone
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
    private val viewModel: ChatViewModel,
    private val isCont: Boolean,
    private val nickName: String,
    private val profileImage: String,
    private val lastIn1Minute: Boolean = true
): BindableItem<RvChatItemReceiveImageBinding>() {
    override fun bind(viewBinding: RvChatItemReceiveImageBinding, position: Int) {
        viewBinding.root.tag = message.timestamp

        handleContinue(viewBinding)


        viewModel.downloadImageUrl(
            message = message,
            callback = { uri ->
                Log.e(CHATTING_TAG, uri.toString())
                Glide.with(viewBinding.ivContent)
                    .load(uri)
                    .into(viewBinding.ivContent)
            }
        )
        viewBinding.tvTime.text =  message.timestamp?.seconds?.secondToChatTime() ?: System.currentTimeMillis().millisecondToChatTime()
        viewBinding.tvTime.isGone = !lastIn1Minute
    }

    private fun handleContinue(viewBinding: RvChatItemReceiveImageBinding) {
        if (isCont) {
            viewBinding.tvNickname.apply {
                isGone = false
                text = nickName
            }
            viewBinding.ivProfileImage.isGone = false
            Glide.with(viewBinding.ivProfileImage)
                .load(profileImage)
                .circleCrop()
                .into(viewBinding.ivProfileImage)
        }
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_receive_image

    override fun initializeViewBinding(view: View): RvChatItemReceiveImageBinding =
        RvChatItemReceiveImageBinding.bind(view)
}