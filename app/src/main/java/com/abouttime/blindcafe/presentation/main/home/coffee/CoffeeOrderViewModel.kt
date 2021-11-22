package com.abouttime.blindcafe.presentation.main.home.coffee

import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY
import com.abouttime.blindcafe.common.constants.PREFERENCES_KEY.MATCHING_ID
import com.abouttime.blindcafe.data.server.dto.matching.PostDrinkDto
import com.abouttime.blindcafe.domain.use_case.PostDrinkUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CoffeeOrderViewModel(
    private val postDrinkUseCase: PostDrinkUseCase
): BaseViewModel() {
    private val _nextButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val nextButton: LiveData<Boolean> get() = _nextButton

    var matchingId: Int? = null
    var startTime: String? = null
    var partnerNickname: String? = null


    var currentSelect: Int? = null

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


    private fun postDrink() {
        matchingId?.let { mId ->
            currentSelect?.let { drink ->
                postDrinkUseCase(mId, PostDrinkDto(drink)).onEach { response ->
                    when (response) {
                        is Resource.Loading -> {
                            Log.d(RETROFIT_TAG, "Loading")
                        }
                        is Resource.Success -> {
                            Log.d(RETROFIT_TAG, response.data.toString())
                            response.data?.startTime?.let { time ->
                                startTime = time
                                moveToChatFragment(
                                    matchingId = mId,
                                    startTime = startTime,
                                    partnerNickname = partnerNickname
                                )
                            }
                        }
                        is Resource.Error -> {
                            Log.d(RETROFIT_TAG, response.message.toString())
                            showToast(R.string.toast_check_internet)
                        }
                    }
                }.launchIn(viewModelScope)
            } ?: kotlin.run {
                Log.d(RETROFIT_TAG, "currentSelect 가 null 입니다.")
            }
        } ?: kotlin.run {
            Log.d(RETROFIT_TAG, "matchingId 가 null 입니다.")
        }


    }


    fun onClickNextButton() {
        if (canClickNextButton()) {
            postDrink()
        } else {
            showToast(R.string.coffee_order_next_alert)
        }
    }


        private fun moveToChatFragment(matchingId: Int, startTime: String?, partnerNickname: String?) {
        moveToDirections(CoffeeOrderFragmentDirections.actionCoffeeOrderFragmentToMatchingFragment(
            matchingId = matchingId,
            startTime = startTime,
            partnerNickname = partnerNickname
        ))
    }


}