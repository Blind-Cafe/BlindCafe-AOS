package com.abouttime.blindcafe.presentation

import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.common.util.SingleLiveData

object GlobalLiveData {
    val loadingEvent = SingleLiveData<Boolean>()
    val updateHomeState = SingleLiveData<Boolean>()
    val suspendUserEvent = SingleLiveData<Boolean>()

}