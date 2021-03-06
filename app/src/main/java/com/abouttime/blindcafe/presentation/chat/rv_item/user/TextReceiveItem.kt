package com.abouttime.blindcafe.presentation.chat.rv_item.user

import android.util.Log
import android.view.View
import androidx.core.view.isGone
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.ext.millisecondToChatTime
import com.abouttime.blindcafe.common.ext.secondToChatTime
import com.abouttime.blindcafe.common.ext.setMarginTop
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
        handleSendFirstIn1Minute(viewBinding, position)

        viewBinding.message = message
        viewBinding.tvTime.text =  message.timestamp?.seconds?.secondToChatTime() ?: System.currentTimeMillis().millisecondToChatTime()
        Log.e("zxcv", "$position : ${viewBinding.tvTime.text} 이게 왜 안나와 ${viewModel.sendLastIn1Minute[position]}}" )
        viewBinding.tvTime.isGone = !viewModel.sendLastIn1Minute[position]
        if (isCont) {
            viewBinding.tvTime.setTextColor(viewBinding.tvTime.resources.getColor(R.color.main, null))
        }
    }

    private fun handleContinue(viewBinding: RvChatItemReceiveTextBinding, position: Int) {


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
                viewModel.moveToPartnerProfileFragment()
            }
        }
    }

    private fun handleSendFirstIn1Minute(viewBinding: RvChatItemReceiveTextBinding, position: Int) {
        if (viewModel.sendFirstIn1Minute[position].not()) {
            viewBinding.root.setMarginTop(0)
            if (isCont) {
                viewBinding.tvContentText.setBackgroundResource(R.drawable.bg_matching_receive_text_round)
            } else {
                viewBinding.tvContentText.setBackgroundResource(R.drawable.bg_chat_receive_text_round)
            }
        } else {
            if (isCont) {
                viewBinding.tvContentText.setBackgroundResource(R.drawable.bg_matching_receive_text)
            } else {
                viewBinding.tvContentText.setBackgroundResource(R.drawable.bg_chat_receive_text)
            }

        }
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_receive_text

    override fun initializeViewBinding(view: View): RvChatItemReceiveTextBinding =
        RvChatItemReceiveTextBinding.bind(view)
}