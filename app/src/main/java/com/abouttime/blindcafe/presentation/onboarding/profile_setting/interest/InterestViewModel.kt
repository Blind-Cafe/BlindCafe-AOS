package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel

class InterestViewModel(

) : BaseViewModel() {


    private val _selectedItem = MutableLiveData<MutableList<Int>>(mutableListOf())
    val selectedItem: LiveData<MutableList<Int>> get() = _selectedItem




    fun onClickBackButton() {
        popDirections()
    }


    fun onClickInterestItem(v: View) {
        _selectedItem.value?.let { value ->
            val s = value
            var idx = 0
            when (v.id) {
                R.id.fl_interest_container_1 -> { idx = 0 }
                R.id.fl_interest_container_2 -> { idx = 1 }
                R.id.fl_interest_container_3 -> { idx = 2 }
                R.id.fl_interest_container_4 -> { idx = 3 }
                R.id.fl_interest_container_5 -> { idx = 4 }
                R.id.fl_interest_container_6 -> { idx = 5 }
                R.id.fl_interest_container_7 -> { idx = 6 }
                R.id.fl_interest_container_8 -> { idx = 7 }
                R.id.fl_interest_container_9 -> { idx = 8 }
            }
            if (s.contains(idx)) {
                s.remove(idx)
            } else {
                if (s.size < 3) {
                    s.add(idx)
                } else {
                    showToast(R.string.interest_toast_up_to_3)
                }
            }
            s.let { result ->
                _selectedItem.value = result
            }
        }


    }


    fun onClickNextButton() {
        if (canEnableNextButton()) {
            _selectedItem.value?.let { list ->
                val selectedItemIdx = list.sorted().toMutableList()
                moveToDirections(InterestFragmentDirections.actionInterestFragmentToInterestSubFragment(
                    i1 = selectedItemIdx[0] + 1,
                    i2 = selectedItemIdx[1] + 1,
                    i3 = selectedItemIdx[2] + 1
                ))
            }
        } else {
            showToast(R.string.profile_setting_toast_select_interest)
        }
    }

    fun canEnableNextButton() = _selectedItem.value?.size == 3



}