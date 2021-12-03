package com.abouttime.blindcafe.presentation.chat.rv_item.user

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.view.View
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.ext.millisecondToChatTime
import com.abouttime.blindcafe.common.ext.secondToChatTime
import com.abouttime.blindcafe.databinding.RvChatItemSendAudioBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.xwray.groupie.viewbinding.BindableItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AudioSendItem(
    private val message: Message,
    private val viewModel: ChatViewModel,
) : BindableItem<RvChatItemSendAudioBinding>() {

    @SuppressLint("SetTextI18n")
    override fun bind(viewBinding: RvChatItemSendAudioBinding, position: Int) {
        viewBinding.root.tag = message.timestamp
        var isPlaying = false
        viewModel.downloadAudioUrl(
            message = message,
            callback = { uri ->
                val mediaPlayer = MediaPlayer()
                mediaPlayer.setDataSource(uri.toString())
                val duration = mediaPlayer.duration
                val seconds = (duration / 1000) % 60
                val minutes = ((duration / 1000) / 60) % 60
                viewBinding.tvAudioTime.text = "%02d:%02d".format(seconds, minutes)

                viewBinding.ivPlayController.setOnClickListener {
                    viewBinding.lpiProgress.isClickable = false
                    viewBinding.ivPlayController.isClickable = false

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
            }
        )

//        viewBinding.ivPlayController.setOnClickListener {
//            viewModel.downloadAudioUrl(
//                message = message,
//                callback = { uri ->
//                    viewBinding.lpiProgress.isClickable = false
//                    viewBinding.ivPlayController.isClickable = false
//
//
//                    val mediaPlayer = MediaPlayer()
//                    mediaPlayer.setDataSource(uri.toString())
//
//                    mediaPlayer.setOnPreparedListener { player ->
//                        viewBinding.ivPlayController.setImageResource(R.drawable.bt_pause)
//                        viewBinding.tvAudioTime.startCountUp()
//                        player.start()
//                        isPlaying = true
//
//                        val duration = mediaPlayer.duration
//                        viewModel.viewModelScope.launch {
//                            while (isPlaying) {
//                                viewBinding.lpiProgress.progress =
//                                    ((mediaPlayer.currentPosition / duration.toFloat()) * 100).toInt()
//                                delay(200)
//                            }
//                        }
//                    }
//
//
//                    mediaPlayer.setOnCompletionListener { player ->
//                        viewBinding.ivPlayController.setImageResource(R.drawable.bt_play)
//                        viewBinding.tvAudioTime.stopCountUp()
//                        viewBinding.tvAudioTime.text = "00:00"
//                        viewBinding.lpiProgress.progress = 0
//                        isPlaying = false
//                        viewBinding.lpiProgress.isClickable = true
//                        viewBinding.ivPlayController.isClickable = true
//                        mediaPlayer.release()
//                    }
//
//
//
//
//                    mediaPlayer.prepareAsync()
//
//                }
//            )
//        }

        viewBinding.tvTime.text =
            message.timestamp?.seconds?.secondToChatTime()
                ?: System.currentTimeMillis().millisecondToChatTime()


    }
    private fun startPlay(viewBinding: RvChatItemSendAudioBinding, url: String) {

    }

    override fun getLayout(): Int = R.layout.rv_chat_item_send_audio

    override fun initializeViewBinding(view: View): RvChatItemSendAudioBinding =
        RvChatItemSendAudioBinding.bind(view)

    private fun startPlaying(child: String) {


    }
}