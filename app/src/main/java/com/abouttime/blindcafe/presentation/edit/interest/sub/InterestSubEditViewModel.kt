package com.abouttime.blindcafe.presentation.edit.interest.sub

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.data.remote.server.dto.interest.Interest
import com.abouttime.blindcafe.data.remote.server.dto.interest.InterestX
import com.abouttime.blindcafe.data.remote.server.dto.interest.PostInterestDto
import com.abouttime.blindcafe.domain.use_case.server.GetInterestUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostInterestsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class InterestSubEditViewModel(
    private val getInterestUseCase: GetInterestUseCase,
    private val postInterestsUseCase: PostInterestsUseCase
): BaseViewModel() {


    private val _nextButton = MutableLiveData(false)
    val nextButton: LiveData<Boolean> get() = _nextButton

    private val _interests = SingleLiveData<List<Interest>>()
    val interests: SingleLiveData<List<Interest>> get() = _interests

    val selectedSubInterests = Array(3) { mutableListOf<String>() }
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




    /** use cases **/
    fun getInterest(id1 : Int, id2: Int, id3: Int) {
        getInterestUseCase(id1, id2, id3)
            .onEach { result ->
                when(result) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        result.data?.interests?.let {
                            Log.e(LogTag.RETROFIT_TAG, "$it")
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

    private fun postInterests (postInterestDto: PostInterestDto) {
        postInterestsUseCase(postInterestDto).onEach { result ->
            when (result) {
                is Resource.Loading -> { showLoading() }
                is Resource.Success -> {
                    if (result.data?.code == "1000") {
                        popDirections(R.id.interestEditFragment)
                    } else {
                        showToast(R.string.temp_error)
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


        /** onClick **/
    fun onClickNextButton() {
        if (canEnableNextButton()) {
            val dto = PostInterestDto(
                listOf(
                    InterestX(
                        main = i1,
                        sub = selectedSubInterests[0]
                    ),
                    InterestX(
                        main = i2,
                        sub = selectedSubInterests[1]
                    ),
                    InterestX(
                        main = i3,
                        sub = selectedSubInterests[2]
                    )
                )
            )

            postInterests(dto)


        } else {
            showToast(R.string.profile_setting_toast_select_sub_interest)
        }
    }
    fun updateNextButton() {
        _nextButton.value = canEnableNextButton()
    }

    private fun canEnableNextButton(): Boolean {
        if (selectedSubInterests[0].isNotEmpty() && selectedSubInterests[1].isNotEmpty() && selectedSubInterests[2].isNotEmpty()) {
            return true
        }
        return false
    }

    fun onClickBackButton() {
        popDirections()
    }


}