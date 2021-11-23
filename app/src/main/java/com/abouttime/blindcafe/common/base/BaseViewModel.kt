package com.abouttime.blindcafe.common.base

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.abouttime.BlindCafeApplication.Companion.sharedPreferences
import com.abouttime.blindcafe.common.SingleLiveData
import com.abouttime.blindcafe.common.constants.LogTag

open class BaseViewModel() : ViewModel() {
    private val _toastEvent = SingleLiveData<Int>()
    val toastEvent: SingleLiveData<Int> get() = _toastEvent


    private val _navigationEvent = SingleLiveData<NavDirections?>()
    val navigationEvent: SingleLiveData<NavDirections?> get() = _navigationEvent


    private val _saveStringDataEvent = SingleLiveData<Pair<String, String>>()
    val saveStringDataEvent: SingleLiveData<Pair<String, String>> get() = _saveStringDataEvent
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
    fun popDirections() {
        _navigationEvent.value = null
    }

    fun saveStringData(pair: Pair<String, String?>) {
        sharedPreferences
            .edit()
            .putString(
                pair.first,
                pair.second
            )
            .apply()
    }

    fun getStringData(key: String): String? {
        return sharedPreferences
            .getString(key, null)

    }

    fun showLoading() {
        Log.e(LogTag.RETROFIT_TAG, "Loading")
        //_loadingEvent.value = true
    }
    fun dismissLoading() {
        //_loadingEvent.value = false
    }

}