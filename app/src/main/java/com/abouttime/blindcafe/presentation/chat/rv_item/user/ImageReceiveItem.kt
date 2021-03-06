package com.abouttime.blindcafe.presentation.chat.rv_item.user

import android.util.Log
import android.view.View
import androidx.core.view.isGone
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.ext.*
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
) : BindableItem<RvChatItemReceiveImageBinding>() {
    override fun bind(viewBinding: RvChatItemReceiveImageBinding, position: Int) {
        viewBinding.root.tag = message.timestamp

        handleContinue(viewBinding, position)
        handleSendFirstIn1Minute(viewBinding, position)

        viewModel.downloadImageUrl(
            message = message,
            callback = { uri ->
                viewBinding.ivContent.setChatImage(uri)
                viewBinding.cvContentContainer.setOnClickListener {
                    viewModel.moveToChatImageFragment(
                        imageUrl = uri.toString(),
                        nick = nickName,
                        date = message.timestamp?.seconds?.secondToChatImageTime() ?: ""
                    )
                }

            }
        )





        viewBinding.tvTime.text =
            message.timestamp?.seconds?.secondToChatTime() ?: System.currentTimeMillis()
                .millisecondToChatTime()
        viewBinding.tvTime.isGone = !viewModel.sendLastIn1Minute[position]
        if (isCont) {
            viewBinding.tvTime.setTextColor(viewBinding.tvTime.resources.getColor(R.color.main, null))
        }
    }

    private fun handleContinue(viewBinding: RvChatItemReceiveImageBinding, position: Int) {
        if (isCont) {
            if (viewModel.sendFirstIn1Minute[position].not()) {
                viewBinding.ivProfileImage.visibility = View.INVISIBLE
                viewBinding.tvNickname.visibility = View.GONE
                return
            }


            viewBinding.tvNickname.apply {
                isGone = false
                text = nickName
            }
            viewBinding.ivProfileImage.isGone = false
            if (profileImage.isNotEmpty()) {
                Glide.with(viewBinding.ivProfileImage)
                    .load(profileImage)
                    .circleCrop()
                    .into(viewBinding.ivProfileImage)
            } else {
                viewBinding.ivProfileImage.setImageResource(R.drawable.ic_profile_image_none)
            }

            viewBinding.ivProfileImage.setOnClickListener {
                Log.e("senderUid", message.senderUid.toString())
                viewModel.moveToPartnerProfileFragment()
            }
        }
    }

    private fun handleSendFirstIn1Minute(
        viewBinding: RvChatItemReceiveImageBinding,
        position: Int,
    ) {
        if (viewModel.sendFirstIn1Minute[position].not()) {
            viewBinding.root.setMarginTop(0)
        }
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_receive_image

    override fun initializeViewBinding(view: View): RvChatItemReceiveImageBinding =
        RvChatItemReceiveImageBinding.bind(view)
}