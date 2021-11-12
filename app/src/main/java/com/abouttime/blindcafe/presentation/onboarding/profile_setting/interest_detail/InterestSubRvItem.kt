package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail

import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvChatItemReceiveAudioBinding
import com.abouttime.blindcafe.databinding.RvItemInterestSubBinding
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.xwray.groupie.viewbinding.BindableItem



class InterestSubRvItem(
    private val viewModel: InterestSubViewModel
): BindableItem<RvItemInterestSubBinding>() {

    override fun bind(viewBinding: RvItemInterestSubBinding, position: Int) {

    }

    override fun getLayout(): Int = R.layout.rv_item_interest_sub

    override fun initializeViewBinding(view: View): RvItemInterestSubBinding =
        RvItemInterestSubBinding.bind(view)

}