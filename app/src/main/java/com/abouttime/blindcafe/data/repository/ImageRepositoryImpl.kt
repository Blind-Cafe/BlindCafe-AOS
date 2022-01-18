package com.abouttime.blindcafe.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.data.local.gallery.Image
import com.abouttime.blindcafe.data.local.gallery.ImageDataSourceFactory
import com.abouttime.blindcafe.domain.repository.ImageRepository

class ImageRepositoryImpl(
    private val context: Context
): ImageRepository {
    override fun queryImageList(): LiveData<PagedList<Image?>> {
        val factory = ImageDataSourceFactory(context)
        val pageSize = 30
        return LivePagedListBuilder(factory, pageSize).build()
    }
}