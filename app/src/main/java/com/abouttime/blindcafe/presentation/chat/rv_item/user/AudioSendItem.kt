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
import com.abouttime.blindcafe.databinding.RvChatItemSendAudioBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.xwray.groupie.viewbinding.BindableItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AudioSendItem(
    private val message: Message,
    private val viewModel: ChatViewModel,
    private val isCont: Boolean
) : BindableItem<RvChatItemSendAudioBinding>() {

    override fun bind(viewBinding: RvChatItemSendAudioBinding, position: Int) {
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
        if (isCont) {
            viewBinding.tvTime.setTextColor(viewBinding.tvTime.resources.getColor(R.color.main, null))
        }


    }

    private fun handleSendFirstIn1Minute(viewBinding: RvChatItemSendAudioBinding, position: Int) {
        if (viewModel.sendFirstIn1Minute[position].not()) {
            viewBinding.root.setMarginTop(0)
        }
    }

    private fun startPlay(viewBinding: RvChatItemSendAudioBinding, url: String) {

    }

    override fun getLayout(): Int = R.layout.rv_chat_item_send_audio

    override fun initializeViewBinding(view: View): RvChatItemSendAudioBinding =
        RvChatItemSendAudioBinding.bind(view)

    private fun startPlaying(child: String) {


    }
}