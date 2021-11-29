package com.abouttime.blindcafe.presentation

import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.common.util.SingleLiveData

object GlobalLiveData {
    val loadingEvent = SingleLiveData<Boolean>()
    val UpdateHomeState = SingleLiveData<Boolean>()

}