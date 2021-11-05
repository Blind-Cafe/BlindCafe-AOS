package com.abouttime.blindcafe.presentation.main.matching

import com.abouttime.blindcafe.common.base.BaseViewModel

class MatchingViewModel: BaseViewModel() {

    fun moveToMainFragment() {
        moveToDirections(MatchingFragmentDirections.actionMatchingFragmentToMainFragment())
    }
}