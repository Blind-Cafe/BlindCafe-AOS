package com.abouttime.blindcafe.presentation.chat.rv_item.user

import android.media.MediaPlayer
import android.view.View
import androidx.core.view.isGone
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.ext.millisecondToChatTime
import com.abouttime.blindcafe.common.ext.secondToChatTime
import com.abouttime.blindcafe.common.ext.setMarginTop
import com.abouttime.blindcafe.databinding.RvChatItemReceiveAudioBinding
import com.abouttime.blindcafe.databinding.RvChatItemReceiveImageBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.bumptech.glide.Glide
import com.xwray.groupie.viewbinding.BindableItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AudioReceiveItem(
    private val message: Message,
    private val viewModel: ChatViewModel,
    private val isCont: Boolean,
    private val nickName: String,
    private val profileImage: String
) : BindableItem<RvChatItemReceiveAudioBinding>() {

    override fun bind(viewBinding: RvChatItemReceiveAudioBinding, position: Int) {

        handleContinue(viewBinding, position)
        handleSendFirstIn1Minute(viewBinding, position)

        var isPlaying = false
        viewBinding.ivPlayController.setOnClickListener {
            viewModel.downloadAudioUrl(
                message = message,
                callback = { uri ->
                    viewBinding.lpiProgress.isClickable = false
                    viewBinding.ivPlayController.isClickable = false

                    val mediaPlayer = MediaPlayer()
                    mediaPlayer.setDataSource(uri.toString())
                    mediaPlayer.setOnPreparedListener { player ->
                        viewBinding.ivPlayController.setImageResource(R.drawable.bt_pause)
                        viewBinding.tvAudioTime.startCountUp()
                        player.start()
                        isPlaying = true

                        val duration = mediaPlayer.duration
                        viewModel.viewModelScope.launch {
                            while (isPlaying) {
                                viewBinding.lpiProgress.progress =
                                    ((mediaPlayer.currentPosition / duration.toFloat()) * 100).toInt()
                                delay(200)
                            }
                        }
                    }
                    mediaPlayer.setOnCompletionListener { player ->
                        viewBinding.ivPlayController.setImageResource(R.drawable.bt_play)
                        viewBinding.tvAudioTime.stopCountUp()
                        viewBinding.tvAudioTime.text = "00:00"
                        viewBinding.lpiProgress.progress = 0
                        isPlaying = false
                        viewBinding.lpiProgress.isClickable = true
                        viewBinding.ivPlayController.isClickable = true
                        mediaPlayer.release()
                    }



                    mediaPlayer.prepareAsync()

                }
            )
        }


        viewBinding.tvTime.text =  message.timestamp?.seconds?.secondToChatTime() ?: System.currentTimeMillis().millisecondToChatTime()
        viewBinding.tvTime.isGone = !viewModel.sendLastIn1Minute[position]
    }

    private fun handleContinue(viewBinding: RvChatItemReceiveAudioBinding, position: Int) {
        if (viewModel.sendFirstIn1Minute[position].not()) {
            viewBinding.ivProfileImage.visibility = View.INVISIBLE
            viewBinding.tvNickname.visibility = View.GONE
            return
        }

        if (isCont) {
            viewBinding.tvNickname.apply {
                isGone = false
                text = nickName
            }
            viewBinding.ivProfileImage.isGone = false
            if (profileImage.isNotEmpty()) {
                Glide.with(viewBinding.ivProfileImage.context)
                    .load(profileImage)
                    .circleCrop()
                    .into(viewBinding.ivProfileImage)
            } else {
                viewBinding.ivProfileImage.setImageResource(R.drawable.ic_profile_image_none)
            }
        }
    }

    private fun handleSendFirstIn1Minute(viewBinding: RvChatItemReceiveAudioBinding, position: Int) {
        if (viewModel.sendFirstIn1Minute[position].not()) {
            viewBinding.root.setMarginTop(0)
        }
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_receive_audio

    override fun initializeViewBinding(view: View): RvChatItemReceiveAudioBinding =
        RvChatItemReceiveAudioBinding.bind(view)

}