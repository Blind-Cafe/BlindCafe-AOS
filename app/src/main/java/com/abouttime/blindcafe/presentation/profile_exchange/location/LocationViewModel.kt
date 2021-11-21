package com.abouttime.blindcafe.presentation.profile_exchange.location

import com.abouttime.blindcafe.common.base.BaseViewModel

class LocationViewModel: BaseViewModel() {
    val subLocations = mutableListOf<String>()
    val mainLocations = mutableListOf<String>()




    fun setMainLocation(list: List<String>) {
        mainLocations.clear()
        mainLocations.addAll(list)
    }
    fun setSubLocation(list: List<String>) {
        subLocations.clear()
        subLocations.addAll(list)
    }
}