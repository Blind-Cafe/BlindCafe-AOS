package com.abouttime.blindcafe.presentation.main.my_page.edit.interest.sub

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.common.constants.PreferenceKey
import com.abouttime.blindcafe.data.server.dto.interest.Interest
import com.abouttime.blindcafe.domain.use_case.server.GetInterestUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class InterestSubEditViewModel(
    private val getInterestUseCase: GetInterestUseCase
): BaseViewModel() {


    private val _nextButton = MutableLiveData(false)
    val nextButton: LiveData<Boolean> get() = _nextButton

    private val _interests = SingleLiveData<List<Interest>>()
    val interests: SingleLiveData<List<Interest>> get() = _interests

    val selectedSubInterests = Array(3) { mutableListOf<String>() }


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
        getInterestUseCase(id1, id2, id3)
            .onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        Log.e(LogTag.RETROFIT_TAG, "Loading")
                    }
                    is Resource.Success -> {
                        result.data?.interests?.let {
                            Log.e(LogTag.RETROFIT_TAG, "$it")
                            _interests.postValue(it)
                        }

                    }
                    is Resource.Error -> {
                        Log.e(LogTag.RETROFIT_TAG, "${result?.message}")
                    }
                }
            }.launchIn(viewModelScope)
    }


    fun onClickNextButton() {

    }


}