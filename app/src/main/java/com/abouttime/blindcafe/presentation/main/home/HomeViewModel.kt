package com.abouttime.blindcafe.presentation.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.domain.use_case.GetHomeInfoUseCase
import com.abouttime.blindcafe.domain.use_case.PostFcmUseCase
import com.abouttime.blindcafe.domain.use_case.PostMatchingRequestUseCase
import com.abouttime.blindcafe.domain.use_case.PostNotificationUseCase
import com.abouttime.blindcafe.presentation.main.MainFragmentDirections
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeInfoUseCase: GetHomeInfoUseCase,
    private val postMatchingRequestUseCase: PostMatchingRequestUseCase
): BaseViewModel() {
    private val _homeStatusCode: MutableLiveData<Int> = MutableLiveData<Int>(-1)
    val homeStatusCode: LiveData<Int> get() = _homeStatusCode
    private val _time: MutableLiveData<String> = MutableLiveData("00:00")
    val time: LiveData<String> get() = _time

    private var matchingId: Int? = null
    private var startTime: String? = null
    private var reason: String? = null
    private var partnerNickname: String? = null


    init {
        testHomeState()
        //getHomeInfo()
    }

    private fun getHomeInfo() {
        getHomeInfoUseCase().onEach { resource ->
            when(resource) {
                is Resource.Loading -> {
                    Log.d(RETROFIT_TAG, "Loading")
                }
                is Resource.Success -> {
                    Log.d(RETROFIT_TAG, resource.data.toString())
                    resource.data?.matchingStatus?.let { status ->
                        _homeStatusCode.postValue(getHomeStatusCode(status))
                    }
                    resource.data?.let {
                        matchingId = it.matchingId
                        startTime = it.startTime
                        reason = it.reason
                        partnerNickname = it.partnerNickname
                    }
                }
                is Resource.Error -> {
                    Log.d(RETROFIT_TAG, resource.message.toString())
                }
            }

        }.launchIn(viewModelScope)
    }



    private fun testHomeState() = viewModelScope.launch {
        delay(5000)
        _homeStatusCode.postValue(0)
        delay(5000)
        _homeStatusCode.postValue(1)
        delay(5000)
        _homeStatusCode.postValue(2)
        delay(5000)
        _homeStatusCode.postValue(3)
    }




    private fun postMatchingRequest() {
        postMatchingRequestUseCase().onEach { response ->
            when(response) {
                is Resource.Loading -> {
                    Log.d(RETROFIT_TAG, "Loading")
                }
                is Resource.Success -> {
                    Log.d(RETROFIT_TAG, response.data.toString())
                    response.data?.matchingStatus?.let { status ->
                        _homeStatusCode.postValue(getHomeStatusCode(status))
                    }
                }
                is Resource.Error -> {
                    Log.d(RETROFIT_TAG, response.message.toString())
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getHomeStatusCode(status: String): Int {
        return when(status) {
            "NONE" -> 0
            "WAIT" -> 1
            "FOUND" -> 2
            "MATCHING" -> 3
            "FAILED_LEAVE_ROOM" -> 4
            "FAILED_REPORT" -> 5
            "FAILED_WONT_EXCHANGE" -> 6
            else -> 0
        }

    }

    /** onClick **/
    fun onClickCircleImageView() {
        val statusCode = _homeStatusCode.value


        when (statusCode) {
            0 -> { // 매칭 없음
                postMatchingRequest()
            }
            1 -> { // 매칭 대기
                showToast(R.string.toast_matching_wait)
            }
            2 -> { // 음료수 미선택
                matchingId?.let { id ->
                    moveToCoffeeOrderFragment(
                        matchingId = id,
                        startTime = startTime
                    )
                }

            }
            3 -> { // 매칭 + 음료선택 완료
                matchingId?.let { id ->
                    moveToChatFragment(
                        matchingId = id,
                        startTime = startTime
                    )
                }
            }
            4, 5, 6 -> { // 방 폭파 or 프로필 교환 거절
                if (partnerNickname != null && reason != null) {
                    moveToExitFragment(partnerNickname, reason)
                }
            }
            else -> {}
        }
    }



    /** navigation **/
    private fun moveToChatFragment(matchingId: Int, startTime: String?) {
        moveToDirections(MainFragmentDirections.actionMainFragmentToMatchingFragment(
            matchingId = matchingId,
            startTime = startTime
        ))
    }
    private fun moveToCoffeeOrderFragment(matchingId: Int, startTime: String? ) {
        moveToDirections(MainFragmentDirections.actionMainFragmentToCoffeeOrderFragment(
            matchingId = matchingId,
            startTime = startTime
        ))
    }

    fun moveToProfileExchangeFragment() {
        moveToDirections(MainFragmentDirections.actionMainFragmentToProfileExchangeFragment())
    }

    fun moveToExitFragment(partnerNickname: String?, reason: String?) {
        moveToDirections(MainFragmentDirections.actionMainFragmentToExitFragment(
            partnerNickname = partnerNickname,
            reason = reason
        ))
    }




}