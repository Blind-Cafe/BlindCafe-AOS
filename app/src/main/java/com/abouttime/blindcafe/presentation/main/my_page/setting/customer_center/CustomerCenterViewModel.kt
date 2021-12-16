package com.abouttime.blindcafe.presentation.main.my_page.setting.customer_center

import com.abouttime.blindcafe.common.base.BaseViewModel

class CustomerCenterViewModel: BaseViewModel() {


    fun onClickTermButton() {
        moveToTermFragment()
    }
    fun onClickPolicyFragment() {
        moveToPolicyFragment()
    }
    fun onClickBackButton() {
        popDirections()
    }


    private fun moveToTermFragment() {
        moveToDirections(CustomerCenterFragmentDirections.actionCustomerCenterFragmentToTermFragment())
    }
    private fun moveToPolicyFragment() {
        moveToDirections(CustomerCenterFragmentDirections.actionCustomerCenterFragmentToPolicyFragment())
    }
}