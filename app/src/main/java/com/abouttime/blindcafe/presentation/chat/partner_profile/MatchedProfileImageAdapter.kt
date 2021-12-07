package com.abouttime.blindcafe.presentation.chat.partner_profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.common.ext.setImageUrl
import com.abouttime.blindcafe.databinding.VpItemPartnerProfileImageBinding
import com.abouttime.blindcafe.databinding.VpItemProfileImageBinding
import com.bumptech.glide.Glide


class MatchedProfileImageAdapter(): RecyclerView.Adapter<MatchedProfileImageAdapter.ViewHolder>() {


    private val profileImageUrls = mutableListOf<String>()

    inner class ViewHolder(private val binding: VpItemPartnerProfileImageBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
            binding.ivProfileImage.setImageUrl(url)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            VpItemPartnerProfileImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(profileImageUrls[position])
    }

    // 몇개의 뷰를 보여줄 것인가
    override fun getItemCount(): Int = profileImageUrls.size

    fun submitList(list: List<String>) {
        profileImageUrls.clear()
        profileImageUrls.addAll(list)
        notifyDataSetChanged()
    }


}