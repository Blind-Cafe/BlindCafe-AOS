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
    private val onClickItem: (Int) -> Unit
) : PagedListAdapter<Image, GalleryRvAdapter.ViewHolder>(diffUtil) {


    inner class ViewHolder(private val binding: RvGalleryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Image?, position: Int) {
            Glide.with(binding.ivGalleryImage)
                .load(data?.uri)
                .into(binding.ivGalleryImage)

            viewModel.imageSelector.getNumberByIndex(position)?.let { num ->
                binding.tvGallerySelect.setBackgroundResource(R.drawable.bg_gallery_select_image)
                binding.tvGallerySelect.text = num.toString()
            } ?: kotlin.run {
                binding.tvGallerySelect.setBackgroundResource(R.drawable.bg_gallery_unselect_image)
                binding.tvGallerySelect.text = ""
            }
        }

        fun bindView(data: Image?, position: Int) {
            binding.ivGalleryImage.setOnClickListener {

                data?.let { image ->
                    val result = viewModel.imageSelector.clickItem(image, position)
                    if (result.not()) {
                        showAlertImageCntLimitToast()
                    }
                    notifyDataSetChanged()
                }
                onClickItem(viewModel.imageSelector.size)
            }
        }

        private fun showAlertImageCntLimitToast() {
            Toast.makeText(binding.root.context,
                binding.root.resources.getString(R.string.toast_gallery_limit),
                Toast.LENGTH_SHORT).show()
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