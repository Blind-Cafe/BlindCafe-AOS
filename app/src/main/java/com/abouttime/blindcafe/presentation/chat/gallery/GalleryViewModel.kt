package com.abouttime.blindcafe.presentation.chat.gallery

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel

class GalleryViewModel(
): BaseViewModel() {
    private val _cursor = MutableLiveData<Cursor?> ()
    val cursor: LiveData<Cursor?> get() = _cursor

//    val images: LiveData<PagedList<Image?>> by lazy {
//        fetchImagesUseCase()
//    }





}