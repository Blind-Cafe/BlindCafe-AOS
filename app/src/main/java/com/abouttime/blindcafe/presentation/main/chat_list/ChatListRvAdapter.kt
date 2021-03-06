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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ChatListRvAdapter(
    private val viewModel: ChatListViewModel,
) : RecyclerView.Adapter<ChatListRvAdapter.ViewHolder>() {
    private val matches = mutableListOf<Matching>()

    inner class ViewHolder(private val binding: RvChatListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindData(match: Matching, position: Int) = with(binding)  {
            vRead.isGone = (match.received == true)
            setColorByRead()

            tvLastMessage.text = match.latestMessage
            tvTimeRemaining.text = "${match.expiryTime}"

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
                    match?.partner?.userId?.let { pId ->
                        viewModel.getChatRoomInfo(match.matchingId, pId)
                    }
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