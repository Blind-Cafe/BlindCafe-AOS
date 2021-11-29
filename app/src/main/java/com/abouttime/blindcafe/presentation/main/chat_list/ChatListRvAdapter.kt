package com.abouttime.blindcafe.presentation.main.chat_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.data.server.dto.matching.Matching
import com.abouttime.blindcafe.data.server.dto.matching.Partner
import com.abouttime.blindcafe.databinding.RvChatListItemBinding
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName


class ChatListRvAdapter(
    private val viewModel: ChatListViewModel,
) : RecyclerView.Adapter<ChatListRvAdapter.ViewHolder>() {
    private val matches = mutableListOf<Matching>()

    inner class ViewHolder(private val binding: RvChatListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindData(match: Matching, position: Int) = with(binding)  {
            vRead.isGone = match.received != true
            setColorByRead()

            tvLastMessage.text = match.latestMessage
            tvTimeRemaining.text = "${match.expiryDay}일 남음"

            match?.matchingId?.let { id ->
                viewModel.subscribeLastMessage(id) { m ->
                    val lastContent = when (m.type) {
                        1 -> {
                             m.contents
                        }
                        2 -> {
                            "사진을 보냈습니다."
                        }
                        3 -> {
                            "음성메시지를 보냈습니다."
                        }
                        4, 5, 6 -> {
                            "토픽이 도착했습니다."
                        }
                        else -> tvLastMessage.text.toString()
                    }
                    matches.removeAt(position)
                    Matching(
                        expiryDay = match.expiryDay,
                        latestMessage = lastContent,
                        matchingId = match.matchingId,
                        partner = match.partner,
                        received = true
                    )
                    matches.add(0, match)
                    notifyDataSetChanged()
                }
            }


            tvNickname.text = match.partner?.nickname

            match.partner?.profileImage?.let { url ->
                Glide.with(ivProfileImage.context)
                    .load(match.partner.profileImage)
                    .circleCrop()
                    .into(ivProfileImage)
            }
        }
        fun bindView(match: Matching) = with(binding) {
            root.setOnClickListener {
                match.matchingId?.let {
                    viewModel.getChatRoomInfo(match.matchingId)
                }
            }
        }

        private fun setColorByRead() = with(binding) {
            if (vRead.isGone) {
                tvNickname.setTextColor(tvNickname.context.getColor(R.color.gray_300))
                tvLastMessage.setTextColor(tvLastMessage.context.getColor(R.color.chat_list_not_read_content))
            } else {
                tvNickname.setTextColor(tvNickname.context.getColor(R.color.white_2))
                tvLastMessage.setTextColor(tvLastMessage.context.getColor(R.color.white_2))
            }
        }
    }

    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RvChatListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

 
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(matches[position], position)
        holder.bindView(matches[position])
    }

    override fun getItemCount(): Int = matches.size

    fun submitList(list: List<Matching>) {
        matches.clear()
        matches.addAll(list)
        notifyDataSetChanged()
    }


}