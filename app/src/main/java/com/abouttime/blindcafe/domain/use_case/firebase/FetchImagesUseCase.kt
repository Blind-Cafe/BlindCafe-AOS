package com.abouttime.blindcafe.domain.use_case.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.data.gallery.Image
import com.abouttime.blindcafe.domain.repository.ImageRepository

/**
 * GalleyFragmentDialog
 */
class FetchImagesUseCase(
    private val imageRepository: ImageRepository
) {
    operator fun invoke(): LiveData<PagedList<Image?>> {
        Log.d(LogTag.PAGING_TAG, "FetchImagesUseCase invoke")
        return imageRepository.queryImageList()
    }
}