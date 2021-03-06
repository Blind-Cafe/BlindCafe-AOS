package com.abouttime.blindcafe.presentation.chat.rv_item.user

import android.view.View
import androidx.core.view.isGone
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.ext.*
import com.abouttime.blindcafe.common.ext.millisecondToChatTime
import com.abouttime.blindcafe.common.ext.secondToChatTime
import com.abouttime.blindcafe.common.ext.setChatImage
import com.abouttime.blindcafe.databinding.RvChatItemReceiveImageBinding
import com.abouttime.blindcafe.databinding.RvChatItemSendImageBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.xwray.groupie.viewbinding.BindableItem


class ImageSendItem(
    private val message: Message,
    private val viewModel: ChatViewModel,
    private val isCont: Boolean
) : BindableItem<RvChatItemSendImageBinding>() {
    override fun bind(viewBinding: RvChatItemSendImageBinding, position: Int) {
        handleSendFirstIn1Minute(viewBinding, position)
        viewModel.downloadImageUrl(
            message = message,
            callback = { uri ->
                viewBinding.ivContent.setChatImage(uri)

                viewBinding.cvContentContainer.setOnClickListener {
                        viewModel.moveToChatImageFragment(
                            imageUrl = uri.toString(),
                            nick = viewModel.myNickname ?: "",
                            date = message.timestamp?.seconds?.secondToChatImageTime() ?: ""
                        )
                }
            }
        )



        viewBinding.tvTime.text =  message.timestamp?.seconds?.secondToChatTime() ?: System.currentTimeMillis().millisecondToChatTime()
        viewBinding.tvTime.isGone = !viewModel.sendLastIn1Minute[position]
        if (isCont) {
            viewBinding.tvTime.setTextColor(viewBinding.tvTime.resources.getColor(R.color.main, null))
        }

    }

    private fun handleSendFirstIn1Minute(viewBinding: RvChatItemSendImageBinding, position: Int) {
        if (viewModel.sendFirstIn1Minute[position].not()) {
            viewBinding.root.setMarginTop(0)
        }
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_send_image

    override fun initializeViewBinding(view: View): RvChatItemSendImageBinding =
        RvChatItemSendImageBinding.bind(view)
}