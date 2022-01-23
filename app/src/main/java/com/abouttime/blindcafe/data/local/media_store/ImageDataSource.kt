package com.abouttime.blindcafe.data.local.media_store

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import androidx.paging.PositionalDataSource

class ImageDataSource(
    private val context: Context,
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

            Log.d("image tag", "$requestedSize, $pageSize, $imageCount")
            val list: List<Image?> = if (imageCount > requestedSize) {
                getMediaList(c, requestedSize)
            } else if (imageCount > pageSize) {
                getMediaList(c, requestedSize)
            } else {
                getMediaList(c, imageCount)
            }
            callback.onResult(list, 0, c.count)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Image?>) {
        Log.d("image tag", "${params.loadSize}")
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
                    val uri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    imageList.add(Image(
                        id = id,
                        uri = uri
                    ))
                }
            }
        }
        return imageList
    }
}