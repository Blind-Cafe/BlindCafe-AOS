package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.PreferenceKey.MAIN_INTEREST
import com.abouttime.blindcafe.common.constants.PreferenceKey.SUB_INTEREST1
import com.abouttime.blindcafe.common.constants.PreferenceKey.SUB_INTEREST2
import com.abouttime.blindcafe.common.constants.PreferenceKey.SUB_INTEREST3
import com.abouttime.blindcafe.data.remote.server.dto.interest.Interest
import com.abouttime.blindcafe.domain.use_case.remote.server.GetInterestUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class InterestSubViewModel(
    private val getInterestUseCase: GetInterestUseCase,
): BaseViewModel() {

    private val _nextButton = MutableLiveData(false)
    val nextButton: LiveData<Boolean> get() = _nextButton

    private val _interests = SingleLiveData<List<Interest>>()
    val interests: SingleLiveData<List<Interest>> get() = _interests

    var selectedSubInterests = Array(3) { mutableListOf<String>() }



    var i1 = 0
    var i2 = 0
    var i3 = 0

    val interestMap = mapOf(
        1 to "취업",
        2 to "작품",
        3 to "동물",
        4 to "음식",
        5 to "여행",
        6 to "게임",
        7 to "연예",
        8 to "스포츠",
        9 to "재테크"
    )


    fun updateNextButton() {
      _nextButton.value = canEnableNextButton()
    }

    private fun canEnableNextButton(): Boolean {
        if (selectedSubInterests[0].isNotEmpty() && selectedSubInterests[1].isNotEmpty() && selectedSubInterests[2].isNotEmpty()) {
            return true
        }
        return false
    }


    fun getInterest(id1 : Int, id2: Int, id3: Int) {
        i1 = id1
        i2 = id2
        i3 = id3
        getInterestUseCase(id1, id2, id3)
            .onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        result.data?.interests?.let {
                            selectedSubInterests = Array(3) { mutableListOf<String>() }
                            _nextButton.value = false
                            _interests.postValue(it)
                        }
                        dismissLoading()
                    }

                    is Resource.Error -> {
                        if (result.message == "400") {
                            showToast(R.string.toast_fail)
                        } else {
                            showToast(R.string.toast_check_internet)
                        }
                        dismissLoading()
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun onClickNextButton() {
        if (canEnableNextButton()) {
            moveToSigninFragment()
        } else {
            showToast(R.string.profile_setting_toast_select_sub_interest)
        }
    }

    fun onClickBackButton() {
        popDirections()
    }


    private fun moveToSigninFragment() {
        if (i1 != 0 && i2 != 0 && i3 != 0) {
            val mainString = "$i1,$i2,$i3"
            saveStringData(Pair(MAIN_INTEREST, mainString))

            val subInterest1 = selectedSubInterests[0].joinToString(",") { it }
            val subInterest2 = selectedSubInterests[1].joinToString(",") { it }
            val subInterest3 = selectedSubInterests[2].joinToString(",") { it }
            saveStringData(Pair(SUB_INTEREST1, subInterest1))
            saveStringData(Pair(SUB_INTEREST2, subInterest2))
            saveStringData(Pair(SUB_INTEREST3, subInterest3))
            moveToDirections(InterestSubFragmentDirections.actionInterestSubFragmentToSigninFragment())
        }

    }
}