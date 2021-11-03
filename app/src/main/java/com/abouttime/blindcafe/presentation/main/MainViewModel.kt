package com.abouttime.blindcafe.presentation.main

import com.abouttime.blindcafe.common.base.BaseViewModel

class MainViewModel: BaseViewModel() {

    fun moveToMatchingFragment() {
        moveToDirections(MainFragmentDirections.actionMainFragmentToMatchingFragment())
    }
}