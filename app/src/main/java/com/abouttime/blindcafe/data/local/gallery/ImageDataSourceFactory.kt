package com.abouttime.blindcafe.data.local.gallery

import android.content.Context
import android.util.Log
import androidx.paging.DataSource
import com.abouttime.blindcafe.common.constants.LogTag

class ImageDataSourceFactory(
    private val context: Context
): DataSource.Factory<Int, Image?>() {
    override fun create(): DataSource<Int, Image?> {
        val dataSource = ImageDataSource(context)
        return dataSource
    }
}