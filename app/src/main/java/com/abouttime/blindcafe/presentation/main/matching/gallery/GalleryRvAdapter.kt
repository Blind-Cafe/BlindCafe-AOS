package com.abouttime.blindcafe.presentation.main.matching.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.RvGalleryItemBinding

class GalleryRvAdapter(

): RecyclerView.Adapter<GalleryRvAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: RvGalleryItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            binding.ivGalleryImage.setImageDrawable(binding.ivGalleryImage.context.getDrawable(R.drawable.bg_barista))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvGalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 50
}