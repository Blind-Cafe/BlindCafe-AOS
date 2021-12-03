package com.abouttime.blindcafe.presentation.chat.rv_item.common

import android.media.MediaPlayer
import android.util.Log
import android.view.View
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvChatItemTopicAudioBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.xwray.groupie.viewbinding.BindableItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AudioTopicItem(
    private val message: Message,
    private val viewModel: ChatViewModel,
) : BindableItem<RvChatItemTopicAudioBinding>() {

    override fun bind(viewBinding: RvChatItemTopicAudioBinding, position: Int) {
        viewBinding.root.tag = message.timestamp


        var isPlaying = false
        viewBinding.ivPlayController.setOnClickListener {
            message.contents?.let { uri ->
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

        }


    }


    override fun getLayout(): Int = R.layout.rv_chat_item_topic_audio

    override fun initializeViewBinding(view: View): RvChatItemTopicAudioBinding =
        RvChatItemTopicAudioBinding.bind(view)


}