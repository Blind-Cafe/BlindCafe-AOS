package com.abouttime.blindcafe.presentation.main.matching.gallery

import android.content.Context
import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.data.gallery.Image
import com.abouttime.blindcafe.data.gallery.MediaStoreAdapter
import com.abouttime.blindcafe.domain.repository.ImageRepository
import com.abouttime.blindcafe.domain.use_case.FetchImagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GalleryViewModel(
    private val fetchImagesUseCase: FetchImagesUseCase
): BaseViewModel() {
    private val _cursor = MutableLiveData<Cursor?> ()
    val cursor: LiveData<Cursor?> get() = _cursor

    val images: LiveData<PagedList<Image?>> by lazy {
        fetchImagesUseCase()
    }





}