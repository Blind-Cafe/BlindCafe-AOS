package com.abouttime.blindcafe.presentation.chat.gallery

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.data.gallery.Image
import com.abouttime.blindcafe.data.gallery.MediaStoreAdapter
import com.abouttime.blindcafe.domain.use_case.firebase.FetchImagesUseCase

class GalleryViewModel(
    private val fetchImagesUseCase: FetchImagesUseCase
): BaseViewModel() {
    private val _cursor = MutableLiveData<Cursor?> ()
    val cursor: LiveData<Cursor?> get() = _cursor

//    val images: LiveData<PagedList<Image?>> by lazy {
//        fetchImagesUseCase()
//    }





}