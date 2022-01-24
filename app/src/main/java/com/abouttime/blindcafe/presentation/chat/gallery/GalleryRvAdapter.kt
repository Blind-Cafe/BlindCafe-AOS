package com.abouttime.blindcafe.presentation.chat.gallery

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.data.local.database.entity.MessageEntity
import com.abouttime.blindcafe.data.local.media_store.Image
import com.abouttime.blindcafe.databinding.RvGalleryItemBinding
import com.bumptech.glide.Glide

class GalleryRvAdapter(
    private val viewModel: GalleryViewModel,
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
                 Log.d("asdf", "클릭")
                val size = viewModel.selectedImages.size

                data?.let { image ->
                    if (viewModel.isSelected.contains(position)) {
                        unselectImage(image, position)
                    } else {
                        if (size >= 5) {
                            viewModel.showToast(R.string.toast_gallery_limit)
                            return@setOnClickListener
                        }
                        selectImage(image, size, position)
                    }
                    notifyDataSetChanged()
                    Log.d("asdf", viewModel.isSelected.toString())
                    Log.d("asdf", viewModel.selectedImages.toString())
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
        try {
            val image: Image? = getItem(position)
            image?.let { im ->
                holder.bindData(im, position)
                holder.bindView(im, position)
            }
        } catch (e: Exception) {
            Log.d("image tag", e.toString())
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