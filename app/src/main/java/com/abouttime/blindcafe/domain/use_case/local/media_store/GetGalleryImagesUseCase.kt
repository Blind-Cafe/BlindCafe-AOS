package com.abouttime.blindcafe.domain.use_case.local.media_store

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.abouttime.blindcafe.data.local.media_store.Image
import com.abouttime.blindcafe.domain.repository.ImageRepository

/**
 * GalleyFragmentDialog
 */
class GetGalleryImagesUseCase(
    private val imageRepository: ImageRepository
) {
    operator fun invoke(): LiveData<PagedList<Image?>> {
        return imageRepository.queryImageList()
    }
}