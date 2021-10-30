package com.abouttime.blindcafe.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.abouttime.blindcafe.common.SingleLiveData

class NavHostViewModel: ViewModel() {
    private val _navDirectionEvent = SingleLiveData<NavDirections>()
    val navDirectionEvent: SingleLiveData<NavDirections> = _navDirectionEvent




    fun moveToDirections(directions: NavDirections) {
        Log.d("asdf", "NavHostViewModel 의 moveToDriections")
        _navDirectionEvent.value = directions
    }




}