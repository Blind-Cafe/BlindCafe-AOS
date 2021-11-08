package com.example.chatexample.presentation.ui.chat.rv_item

import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvChatItemReceiveAudioBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.main.matching.MatchingViewModel
import com.xwray.groupie.viewbinding.BindableItem


class AudioReceiveItem(
    private val message: Message,
    private val onClickAudioMessage: (Message) -> Unit = {},
    private val viewModel: MatchingViewModel
): BindableItem<RvChatItemReceiveAudioBinding>() {

    override fun bind(viewBinding: RvChatItemReceiveAudioBinding, position: Int) {
        viewBinding.root.setOnClickListener {
//            Firebase.storage.reference.child("audio").child(message.contents).downloadUrl.addOnSuccessListener {
//                val mediaPlayer = MediaPlayer()
//                mediaPlayer.setDataSource(it.toString())
//                mediaPlayer.setOnPreparedListener { player ->
//                    viewBinding.audioIvReceive.setImageResource(R.drawable.ic_recorder_stop)
//                    player.start()
//                }
//                mediaPlayer.setOnCompletionListener { player ->
//                    viewBinding.audioIvReceive.setImageResource(R.drawable.ic_recorder_play)
//                    mediaPlayer.release()
//                }
//                mediaPlayer.prepareAsync()
//            }
        }
    }

    override fun getLayout(): Int = R.layout.rv_chat_item_receive_audio

    override fun initializeViewBinding(view: View): RvChatItemReceiveAudioBinding =
        RvChatItemReceiveAudioBinding.bind(view)


}