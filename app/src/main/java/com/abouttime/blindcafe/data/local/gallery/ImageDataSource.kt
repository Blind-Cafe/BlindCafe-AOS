package com.abouttime.blindcafe.data.local.gallery

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import androidx.paging.PositionalDataSource
import com.abouttime.blindcafe.common.constants.LogTag.PAGING_TAG

class ImageDataSource(
    private val context: Context
) : PositionalDataSource<Image?>() {
    private var cursor: Cursor? = null

    init {
        cursor = MediaStoreAdapter().getCursor(context)
        cursor?.moveToFirst()
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Image?>) {

        cursor?.let { c ->
            val requestedSize = params.requestedLoadSize
            val pageSize = params.pageSize
            val imageCount = c.count
            Log.d(PAGING_TAG, "loadInitial $imageCount $requestedSize ${params.requestedLoadSize}")
            if (imageCount > requestedSize) {
                val list: List<Image?> = getMediaList(c, params.requestedLoadSize)
                callback.onResult(list, 0, c.count)
            } else if (imageCount > pageSize) {
                val list: List<Image?> = getMediaList(c, params.requestedLoadSize)
                callback.onResult(list, 0, c.count)
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Image?>) {
        Log.d(PAGING_TAG, "loadRange")
        val list: List<Image?> = getMediaList(cursor, params.loadSize)
        callback.onResult(list)
    }

    @SuppressLint("Range")
    private fun getMediaList(cursor: Cursor?, loadSize: Int): ArrayList<Image?> {
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