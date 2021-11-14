package com.abouttime.blindcafe.presentation.main.home.coffee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel

class CoffeeOrderViewModel: BaseViewModel() {
    private val _nextButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val nextButton: LiveData<Boolean> get() = _nextButton

    val resIds: List<Int> = mutableListOf(
        R.drawable.bt_drink_1,
        R.drawable.bt_drink_2,
        R.drawable.bt_drink_3,
        R.drawable.bt_drink_4,
        R.drawable.bt_drink_5,
        R.drawable.bt_drink_6,
        R.drawable.bt_drink_7,
        R.drawable.bt_drink_8,
        R.drawable.bt_drink_9,
    )

    val selectedResId: List<Int> = mutableListOf(
        R.drawable.bt_drink_selected_1,
        R.drawable.bt_drink_selected_2,
        R.drawable.bt_drink_selected_3,
        R.drawable.bt_drink_selected_4,
        R.drawable.bt_drink_selected_5,
        R.drawable.bt_drink_selected_6,
        R.drawable.bt_drink_selected_7,
        R.drawable.bt_drink_selected_8,
        R.drawable.bt_drink_selected_9,
    )
    val isSelected = Array(9) { false }


    fun updateNextButton() {
        _nextButton.value = canClickNextButton()
    }
    private fun canClickNextButton(): Boolean {
        for (i in isSelected.indices) {
            if (isSelected[i]) {
                return true
            }
        }
        return false
    }


    fun onClickNextButton() {
        if (canClickNextButton()) {
            moveToChatFragment()
        } else {
            showToast(R.string.coffee_order_next_alert)
        }
    }


    private fun moveToChatFragment() {
        moveToDirections(CoffeeOrderFragmentDirections.actionCoffeeOrderFragmentToMatchingFragment())
    }


}