package com.abouttime.blindcafe.presentation.chat.gallery

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.data.local.media_store.Image
import com.abouttime.blindcafe.databinding.RvGalleryItemBinding
import com.bumptech.glide.Glide

class GalleryRvAdapter(
    private val viewModel: GalleryViewModel,
    private var cursor: Cursor?,
) : PagedListAdapter<Image, GalleryRvAdapter.ViewHolder>(diffUtil) {


    inner class ViewHolder(private val binding: RvGalleryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Image?, position: Int) {
            Glide.with(binding.ivGalleryImage)
                .load(data?.uri)
                .into(binding.ivGalleryImage)

            binding.tvGallerySelect.text = (viewModel.isSelected[position] ?: "").toString()
        }

        fun bindView(data: Image?, position: Int) {
            binding.ivGalleryImage.setOnClickListener {
                val size = viewModel.selectedImages.size

                if (size >= 5) {
                    viewModel.showToast(R.string.toast_gallery_limit)
                    return@setOnClickListener
                }
                data?.let { image ->
                    if (viewModel.isSelected.contains(position)) {
                        unselectImage(image, position)
                    } else {
                        selectImage(image, size, position)
                    }
                    notifyDataSetChanged()
                }

            }
        }


        private fun unselectImage(image: Image, position: Int) {
            viewModel.isSelected.minus(position)
            viewModel.selectedImages.remove(image.uri)

        }

        private fun selectImage(image: Image, size: Int, position: Int) {
            viewModel.isSelected.plus(Pair(position, size + 1))
            viewModel.selectedImages.add(image.uri)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvGalleryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position < currentList!!.size) {
            val image: Image? = currentList!![position]
            holder.bindData(image, position)
            holder.bindView(image, position)
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }
        }
    }
}