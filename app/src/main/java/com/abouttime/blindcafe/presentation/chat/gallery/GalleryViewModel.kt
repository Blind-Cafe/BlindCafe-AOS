package com.abouttime.blindcafe.presentation.chat.gallery

import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.domain.use_case.firebase.FetchImagesUseCase
import java.util.*

class GalleryViewModel(
    private val fetchImagesUseCase: FetchImagesUseCase
): BaseViewModel() {
    private val _cursor = MutableLiveData<Cursor?> ()
    val cursor: LiveData<Cursor?> get() = _cursor

    val selectedImages = LinkedList<Uri>()
    val isSelected = setOf<Int>()

//    val images: LiveData<PagedList<Image?>> by lazy {
//        fetchImagesUseCase()
//    }





}