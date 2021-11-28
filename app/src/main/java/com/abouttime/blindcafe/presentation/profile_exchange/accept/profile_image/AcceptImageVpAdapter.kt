package com.abouttime.blindcafe.presentation.profile_exchange.accept.profile_image

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.databinding.VpItemProfileImageBinding
import com.abouttime.blindcafe.databinding.VpItemRuleBinding
import com.bumptech.glide.Glide


class AcceptImageVpAdapter(): RecyclerView.Adapter<AcceptImageVpAdapter.ViewHolder>() {


    private val resIds = mutableListOf<String>()

    inner class ViewHolder(private val binding: VpItemProfileImageBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(url: String) {
           Glide.with(binding.ivProfileImage.context)
               .load(url)
               .into(binding.ivProfileImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            VpItemProfileImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(resIds[position])
    }

    // 몇개의 뷰를 보여줄 것인가
    override fun getItemCount(): Int = resIds.size

    fun submitList(list: List<String>) {
        resIds.clear()
        resIds.addAll(list)
        notifyDataSetChanged()
    }


}