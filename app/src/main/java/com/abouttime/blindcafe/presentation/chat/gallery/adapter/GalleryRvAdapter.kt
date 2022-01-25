package com.abouttime.blindcafe.presentation.chat.gallery.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.data.local.media_store.Image
import com.abouttime.blindcafe.databinding.RvGalleryItemBinding
import com.abouttime.blindcafe.presentation.chat.gallery.GalleryViewModel
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

            viewModel.isSelected[position]?.let { num ->
                binding.tvGallerySelect.setBackgroundResource(R.drawable.bg_gallery_select_image)
                binding.tvGallerySelect.text = num.toString()
            } ?: kotlin.run {
                binding.tvGallerySelect.setBackgroundResource(R.drawable.bg_gallery_unselect_image)
                binding.tvGallerySelect.text = ""
            }

            binding.tvGallerySelect.text = (viewModel.isSelected[position] ?: "").toString()

        }

        fun bindView(data: Image?, position: Int) {
            binding.ivGalleryImage.setOnClickListener {
                val size = viewModel.selectedImages.size

                data?.let { image ->
                    if (viewModel.isSelected.contains(position)) {
                        unselectImage(image, position)
                    } else {
                        if (size >= 5) {
                            showAlertImageCntLimitToast()
                            return@setOnClickListener
                        }
                        selectImage(image, size, position)
                    }
                    notifyDataSetChanged()
                }
            }
        }
        private fun selectImage(image: Image, size: Int, position: Int) {
            viewModel.isSelected[position] = size + 1
            viewModel.selectedImages.add(image.uri)
        }

        private fun showAlertImageCntLimitToast() {
            Toast.makeText(binding.root.context, binding.root.resources.getString(R.string.toast_gallery_limit), Toast.LENGTH_SHORT).show()
        }

        private fun unselectImage(image: Image, position: Int) {
            updateAllByUnselect(position)
            viewModel.isSelected.remove(position)
            viewModel.selectedImages.remove(image.uri)
        }

        private fun updateAllByUnselect(position: Int) {
            viewModel.apply {
                isSelected.keys.forEach { key ->
                    if (isSelected[key]!! > isSelected[position]!!) {
                        val num = isSelected[key]
                        isSelected[key] = (num!! - 1)
                    }
                }
            }
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