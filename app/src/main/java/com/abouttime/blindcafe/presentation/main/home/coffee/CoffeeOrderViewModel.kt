package com.abouttime.blindcafe.presentation.main.home.coffee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel

class CoffeeOrderViewModel: BaseViewModel() {
    private val _nextButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val nextButton: LiveData<Boolean> get() = _nextButton

    val resIds: List<Int> = mutableListOf(
        R.drawable.bt_coffee_americano,
        R.drawable.bt_coffee_cafe_latte,
        R.drawable.bt_coffee_cafe_mocha,
        R.drawable.bt_coffee_bubble_tea,
        R.drawable.bt_coffee_mint_chocolate,
        R.drawable.bt_coffee_strawberry_smoothie,
        R.drawable.bt_coffee_blue_lemonade,
        R.drawable.bt_coffee_green_tea,
        R.drawable.bt_coffee_grapefruit_tea,
    )

    val selectedResId: List<Int> = mutableListOf(
        R.drawable.bt_coffee_americano_selected,
        R.drawable.bt_coffee_cafe_latte_selected,
        R.drawable.bt_coffee_cafe_mocha_selected,
        R.drawable.bt_coffee_bubble_tea_selected,
        R.drawable.bt_coffee_mint_chocolate_selected,
        R.drawable.bt_coffee_strawberry_smoothie_selected,
        R.drawable.bt_coffee_blue_lemonade_selected,
        R.drawable.bt_coffee_green_tea_selected,
        R.drawable.bt_coffee_grapefruit_tea_selected,
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


}