package com.abouttime.blindcafe.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.abouttime.blindcafe.common.SingleLiveData

class NavHostViewModel: ViewModel() {
    private val _navDirectionEvent = SingleLiveData<NavDirections>()
    val navDirectionEvent: SingleLiveData<NavDirections> = _navDirectionEvent



}