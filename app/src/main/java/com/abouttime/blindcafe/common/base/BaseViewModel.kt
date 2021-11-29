package com.abouttime.blindcafe.common.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.abouttime.BlindCafeApplication.Companion.sharedPreferences
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.presentation.GlobalLiveData

open class BaseViewModel() : ViewModel() {
    private val _toastEvent = SingleLiveData<Int>()
    val toastEvent: SingleLiveData<Int> get() = _toastEvent


    private val _navigationEvent = SingleLiveData<NavDirections>()
    val navigationEvent: SingleLiveData<NavDirections> get() = _navigationEvent
    private val _popNavigationEvent = SingleLiveData<Int?>()
    val popNavigationEvent: SingleLiveData<Int?> get() = _popNavigationEvent

    private val _saveNavigationDataEvent = SingleLiveData<Pair<String, String>>()
    val saveNavigationDataEvent: SingleLiveData<Pair<String, String>> get() = _saveNavigationDataEvent

    private val _getNavigationDataEvent = SingleLiveData<String>()
    val getNavigationDataEvent: SingleLiveData<String> get() = _getNavigationDataEvent


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

    fun popDirections(id: Int? = null) {
        _popNavigationEvent.value = id
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
        Log.e(LogTag.RETROFIT_TAG, "showLoading")
        GlobalLiveData.loadingEvent.postValue(true)
    }
    fun dismissLoading() {
        Log.e(LogTag.RETROFIT_TAG, "dismissLoading")
        GlobalLiveData.loadingEvent.postValue(false)
    }

    fun saveNavigationData(pair: Pair<String, String>) {
        _saveNavigationDataEvent.value = pair
    }




}