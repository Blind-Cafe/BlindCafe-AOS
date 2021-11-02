package com.abouttime.blindcafe.common.base.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.abouttime.blindcafe.common.SingleLiveData

open class BaseViewModel: ViewModel() {
    private val _toastEvent = SingleLiveData<Int>()
    val toastEvent: SingleLiveData<Int> get() = _toastEvent

    private val _navigationEvent = SingleLiveData<NavDirections>()
    val navigationEvent: SingleLiveData<NavDirections> get() = _navigationEvent

    private val _saveStringDataEvent = SingleLiveData<Pair<String, String>>()
    val saveStringDataEvent: SingleLiveData<Pair<String,String>> get() = _saveStringDataEvent
    private val _getStringDataEvent = SingleLiveData<String>()
    val getStringDataEvent: SingleLiveData<String> get() = _getStringDataEvent

    private val _loadingEvent = SingleLiveData<Boolean>()
    val loadingEvent: SingleLiveData<Boolean> get() = _loadingEvent



    fun showToast(resId: Int) {
        _toastEvent.value = resId
    }
    fun moveToDirections(directions: NavDirections) {
        _navigationEvent.value = directions
    }

    fun saveStringData(pair: Pair<String, String>) {
        saveStringDataEvent.value = pair
    }

    fun getStringData(key: String) {
        getStringDataEvent.value = key
    }

}