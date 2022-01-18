package com.abouttime.blindcafe.presentation.chat.gallery

import android.annotation.SuppressLint
import android.content.ContentUris
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.data.local.gallery.Image
import com.abouttime.blindcafe.databinding.RvGalleryItemBinding
import com.bumptech.glide.Glide

class GalleryRvAdapter(
    private val viewModel: GalleryViewModel,
    private var cursor: Cursor?
): RecyclerView.Adapter<GalleryRvAdapter.ViewHolder>() {
    private var list = mutableListOf<Image?>()

    inner class ViewHolder(private val binding: RvGalleryItemBinding): RecyclerView.ViewHolder(binding.root) {

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


        private fun unselectImage(image:Image, position: Int) {
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
        list[position]?.let {
            holder.bindData(list[position], position)
            holder.bindView(list[position], position)
        } ?: kotlin.run {
            Log.e("image", "image is null!\n ${list}")
        }

    }


    override fun getItemCount(): Int = list.size

    fun submitImageList(list: MutableList<Image?>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }



    @SuppressLint("Range")
    private fun getMediaList(loadSize: Int): ArrayList<Image?> {
        val imageList = ArrayList<Image?>()
        cursor?.let { c ->
            repeat(loadSize) {
                if (c.moveToNext()) {
                    val id = c.getLong(c.getColumnIndex(MediaStore.Images.ImageColumns._ID)) ?: 0L
                    val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    imageList.add(Image(uri = uri))
                }
            }
        }
        return imageList
    }
}