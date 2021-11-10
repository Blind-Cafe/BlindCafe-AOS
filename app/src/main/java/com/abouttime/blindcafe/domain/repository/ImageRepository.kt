package com.abouttime.blindcafe.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.abouttime.blindcafe.data.gallery.Image

interface ImageRepository {
    fun queryImageList(): LiveData<PagedList<Image?>>
}