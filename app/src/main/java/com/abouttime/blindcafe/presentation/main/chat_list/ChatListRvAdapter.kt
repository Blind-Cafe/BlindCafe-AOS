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
    }

    // 어떤 뷰를 만들 것인가
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RvChatListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    // 어떤 뷰를 바인드할 것인가
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(matches[position])
    }

    // 몇개의 뷰를 보여줄 것인가
    override fun getItemCount(): Int = matches.size

    fun submitList(list: List<Matching>) {
        matches.clear()
        matches.addAll(list)
        notifyDataSetChanged()
    }


}