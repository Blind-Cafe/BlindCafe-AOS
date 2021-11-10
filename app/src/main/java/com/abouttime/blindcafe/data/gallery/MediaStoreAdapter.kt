package com.abouttime.blindcafe.data.gallery

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import com.abouttime.blindcafe.common.constants.LogTag

class MediaStoreAdapter() {
    val projection = arrayOf(
        MediaStore.Images.ImageColumns._ID,
        MediaStore.Images.ImageColumns.BUCKET_ID,
        MediaStore.Images.ImageColumns.DATE_ADDED,
        MediaStore.Images.ImageColumns.SIZE,
        MediaStore.Images.ImageColumns.MIME_TYPE,
        MediaStore.Images.ImageColumns.ORIENTATION
    )

    fun getCursor(context: Context): Cursor? {
        Log.d(LogTag.PAGING_TAG, "getCursor")
        return context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN+ " DESC"
        )
    }

}