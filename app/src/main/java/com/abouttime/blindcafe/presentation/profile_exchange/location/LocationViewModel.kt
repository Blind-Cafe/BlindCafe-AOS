package com.abouttime.blindcafe.presentation.profile_exchange.location

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.NavigationKey.SELECT_LOCATION

class LocationViewModel: BaseViewModel() {
    val subLocations = mutableListOf<String>()
    var selectedSubLocation = -1

    val mainLocations = mutableListOf<String>()
    var selectedMainLocation = 0




    fun setMainLocation(list: List<String>) {
        mainLocations.clear()
        mainLocations.addAll(list)
    }
    fun setSubLocation(list: List<String>) {
        subLocations.clear()
        subLocations.addAll(list)
    }




    fun onClickBackButton() {
        popDirections()
    }
    fun onClickNextButton() {
        if (selectedSubLocation > -1) {
            saveNavigationData(Pair(SELECT_LOCATION, "${mainLocations[selectedMainLocation]} ${subLocations[selectedSubLocation]}"))
            popDirections()
        } else {
            showToast(R.string.toast_alert_select_location)
        }
    }


}