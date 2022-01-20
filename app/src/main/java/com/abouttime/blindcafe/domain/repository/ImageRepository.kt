package com.abouttime.blindcafe.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.abouttime.blindcafe.data.local.media_store.Image

interface ImageRepository {
    fun queryImageList(): LiveData<PagedList<Image?>>
}