package com.abouttime.blindcafe.presentation.main.home

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.ext.secondToLapseForHome
import com.abouttime.blindcafe.domain.use_case.GetHomeInfoUseCase
import com.abouttime.blindcafe.domain.use_case.PostCancelMatchingUseCase
import com.abouttime.blindcafe.domain.use_case.PostMatchingRequestUseCase
import com.abouttime.blindcafe.presentation.main.MainFragmentDirections
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(
    private val getHomeInfoUseCase: GetHomeInfoUseCase,
    private val postMatchingRequestUseCase: PostMatchingRequestUseCase,
) : BaseViewModel() {
    private val _homeStatusCode: MutableLiveData<Int> = MutableLiveData<Int>(-1)
    val homeStatusCode: LiveData<Int> get() = _homeStatusCode
    private val _time: MutableLiveData<String> = MutableLiveData("00:00")
    val time: LiveData<String> get() = _time


    private var reason: String? = null
    private var partnerNickname: String? = null
    private var matchingId: Int? = null
    var startTime: String? = null
    private var partnerId: Int? = null

    init {
        getHomeInfo()
    }

    /** use cases **/
    fun getHomeInfo() {
        getHomeInfoUseCase().onEach { resource ->
            when (resource) {
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
                        partnerId = it.partnerId
                    }
                }
                is Resource.Error -> {
                    Log.d(RETROFIT_TAG, resource.message.toString())
                }
            }

        }.launchIn(viewModelScope)
    }


    private fun postMatchingRequest() {
        postMatchingRequestUseCase().onEach { response ->
            when (response) {
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
        return when (status) {
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
    fun onClickCircleImageView(v: View) {
        val statusCode = _homeStatusCode.value


        when (statusCode) {
            0 -> { // 매칭 없음
                postMatchingRequest()
            }
            1 -> { // 매칭 대기
                showToast(R.string.toast_matching_wait)
                moveToConfirmDialogFragment(v)
            }
            2 -> { // 음료수 미선택
                matchingId?.let { id ->
                    moveToCoffeeOrderFragment(
                        matchingId = id,
                        startTime = startTime,
                        partnerNickname = partnerNickname
                    )
                }

            }
            3 -> { // 매칭 + 음료선택 완료
                if (matchingId != null && startTime != null && partnerNickname != null) {
                    moveToChatFragment(
                        matchingId = matchingId!!,
                        startTime = startTime!!,
                        partnerNickname = partnerNickname!!
                    )
                }
            }
            4, 5, 6 -> { // 방 폭파 or 프로필 교환 거절
                if (partnerNickname != null && reason != null) {
                    moveToExitFragment(partnerNickname!!, reason!!)
                }
                _time.value = startTime?.toLong()?.secondToLapseForHome()
            }
            else -> {
            }
        }
    }


    /** navigation **/
    private fun moveToChatFragment(matchingId: Int, startTime: String, partnerNickname: String) {
        moveToDirections(MainFragmentDirections.actionMainFragmentToMatchingFragment(
            matchingId = matchingId,
            startTime = startTime,
            partnerNickname = partnerNickname
        ))
    }

    private fun moveToCoffeeOrderFragment(
        matchingId: Int,
        startTime: String?,
        partnerNickname: String?,
    ) {
        moveToDirections(MainFragmentDirections.actionMainFragmentToCoffeeOrderFragment(
            matchingId = matchingId,
            startTime = startTime,
            partnerNickname = partnerNickname
        ))
    }

    fun moveToProfileExchangeFragment() {
        moveToDirections(MainFragmentDirections.actionMainFragmentToProfileExchangeFragment())
    }

    private fun moveToExitFragment(partnerNickname: String, reason: String) {
        moveToDirections(MainFragmentDirections.actionMainFragmentToExitFragment(
            isReport = false,
            title = "%s님이\\\"%s\\\"라는 이유로 대화를 진행하지 못하게되었습니다.\\n\\n아쉽지만 새로운 손님과 또 다른 추억을 쌓을 수 있습니다.".format(partnerNickname, reason) // TODO 리소스로 관리해라
        ))
    }

    private fun moveToConfirmDialogFragment(v: View) = with(v.resources) {
        moveToDirections(
            MainFragmentDirections.actionMainFragmentToConfirmDialogFragment(
                id = R.string.home_matching_cancel_title,
                title = getString(R.string.home_matching_cancel_title),
                subtitle = getString(R.string.home_matching_cancel_subtitle),
                no = getString(R.string.home_matching_cancel_no),
                yes = getString(R.string.home_matching_cancel_yes)
            )
        )
    }




}