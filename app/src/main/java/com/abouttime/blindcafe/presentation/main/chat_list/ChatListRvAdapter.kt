package com.abouttime.blindcafe.presentation.main.chat_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.data.server.dto.matching.Matching
import com.abouttime.blindcafe.databinding.RvChatListItemBinding


class ChatListRvAdapter(
    private val viewModel: ChatListViewModel,
) : RecyclerView.Adapter<ChatListRvAdapter.ViewHolder>() {
    private val matches = mutableListOf<Matching>()

    inner class ViewHolder(private val binding: RvChatListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(match: Matching) = with(binding)  {
            vRead.isGone = match.received != true
            tvNickname.text = match.partner?.nickname
            tvLastMessage.text = match.latestMessage
            tvTimeRemaining.text = "${match?.expiryDay}일 남음"
        }
        fun bindView(match: Matching) = with(binding) {
            root.setOnClickListener {

            }
        }
    }

    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RvChatListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

 
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(matches[position])
        holder.bindData(matches[position])
    }

    override fun getItemCount(): Int = matches.size

    fun submitList(list: List<Matching>) {
        matches.clear()
        matches.addAll(list)
        notifyDataSetChanged()
    }


}