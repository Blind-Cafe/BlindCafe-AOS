package com.abouttime.blindcafe.presentation.main.matching.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.data.gallery.Image
import com.abouttime.blindcafe.databinding.RvGalleryItemBinding
import com.bumptech.glide.Glide

class GalleryRvAdapter(

): RecyclerView.Adapter<GalleryRvAdapter.ViewHolder>() {
    private var list = mutableListOf<Image?>()
    inner class ViewHolder(private val binding: RvGalleryItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Image?) {
            Glide.with(binding.ivGalleryImage)
                .load(data?.uri)
                .into(binding.ivGalleryImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvGalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        list[position]?.let {
            holder.bind(list[position])
        } ?: kotlin.run {
            Log.e("image", "image is null!\n ${list}")
        }

    }

    override fun getItemCount(): Int = list.size

    fun submitImageList(list: MutableList<Image?>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun addImageItem(image: Image) {
        list.add(image)
        notifyItemChanged(list.size - 1)
    }
}