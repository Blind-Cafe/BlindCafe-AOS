package com.abouttime.blindcafe.presentation.chat.chat_image

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.ext.setImageUrl
import com.abouttime.blindcafe.databinding.FragmentChatImageBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ChatImageFragment: BaseFragment<ChatImageViewModel>(R.layout.fragment_chat_image) {
    override val viewModel: ChatImageViewModel by viewModel()
    private var binding: FragmentChatImageBinding? = null
    private val args: ChatImageFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentChatImageBinding = FragmentChatImageBinding.bind(view)
        binding = fragmentChatImageBinding
        initArgs()
        initToolbar()
        initBackButton()
    }


    private fun initArgs() {
        val imageUrl = args.imageUrl
        val nickname = args.nickname
        val date = args.date
        initViews(imageUrl, nickname, date)
    }

    private fun initViews(imageUrl: String, nick: String, date: String) {
        binding?.let { b ->
            b.ivChatImage.setImageUrl(imageUrl)
            b.tvNickname.text = nick
            b.tvDate.text = date
        }
    }
    private fun initToolbar() {
        binding?.let { b ->
            b.ivChatImage.setOnClickListener {
               b.clToolbarContainer.isGone = b.clToolbarContainer.isGone.not()
            }
        }
    }
    private fun initBackButton() {
        binding?.let { b ->
            b.ivBack.setOnClickListener {
                popOneDirections()
            }
        }
    }
}