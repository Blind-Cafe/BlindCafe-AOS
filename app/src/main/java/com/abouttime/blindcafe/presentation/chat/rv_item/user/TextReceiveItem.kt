package com.abouttime.blindcafe.presentation.chat.rv_item.user

import android.view.View
import androidx.core.view.isGone
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.ext.millisecondToChatTime
import com.abouttime.blindcafe.common.ext.secondToChatTime
import com.abouttime.blindcafe.databinding.RvChatItemReceiveTextBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.bumptech.glide.Glide
import com.xwray.groupie.viewbinding.BindableItem

class TextReceiveItem(
    private val viewModel: ChatViewModel,
    private val message: Message,
    private val isCont: Boolean,
    private val nickName: String,
    private val profileImage: String,

): BindableItem<RvChatItemReceiveTextBinding>() {
    override fun bind(viewBinding: RvChatItemReceiveTextBinding, position: Int) {
        handleContinue(viewBinding, position)

        viewBinding.message = message
        viewBinding.tvTime.text =  message.timestamp?.seconds?.secondToChatTime() ?: System.currentTimeMillis().millisecondToChatTime()
        viewBinding.tvTime.isGone = !viewModel.sendLastIn1Minute[position]
    }

    private fun handleContinue(viewBinding: RvChatItemReceiveTextBinding, position: Int) {
        if (viewModel.sendFirstIn1Minute[position].not()) {
            viewBinding.ivProfileImage.visibility = View.INVISIBLE
            viewBinding.tvNickname.visibility = View.GONE
            return
        }


        if (isCont|| isCont.not()) {
            viewBinding.tvNickname.apply {
                isGone = false
                text = nickName
            }
            viewBinding.ivProfileImage.isGone = false
            Glide.with(viewBinding.ivProfileImage)
                .load(profileImage)
                .circleCrop()
                .into(viewBinding.ivProfileImage)

            if (profileImage.isEmpty())
                viewBinding.ivProfileImage.setImageResource(R.drawable.ic_profile_image_none)
        }
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_receive_text

    override fun initializeViewBinding(view: View): RvChatItemReceiveTextBinding =
        RvChatItemReceiveTextBinding.bind(view)
}