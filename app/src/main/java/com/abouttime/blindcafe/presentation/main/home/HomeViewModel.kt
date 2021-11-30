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
import com.abouttime.blindcafe.data.server.dto.user_info.partner.GetPartnerProfileDto
import com.abouttime.blindcafe.domain.model.ChatRoom
import com.abouttime.blindcafe.domain.use_case.server.GetChatRoomInfoUseCase
import com.abouttime.blindcafe.domain.use_case.server.GetHomeInfoUseCase
import com.abouttime.blindcafe.domain.use_case.server.GetPartnerProfileUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostMatchingRequestUseCase
import com.abouttime.blindcafe.presentation.main.MainFragmentDirections
import com.abouttime.blindcafe.presentation.main.home.HomeState.FAILED_LEAVE_ROOM
import com.abouttime.blindcafe.presentation.main.home.HomeState.FAILED_REPORT
import com.abouttime.blindcafe.presentation.main.home.HomeState.FAILED_WONT_EXCHANGE
import com.abouttime.blindcafe.presentation.main.home.HomeState.FOUND
import com.abouttime.blindcafe.presentation.main.home.HomeState.MATCHING
import com.abouttime.blindcafe.presentation.main.home.HomeState.MATCHING_CONTINUE
import com.abouttime.blindcafe.presentation.main.home.HomeState.NONE
import com.abouttime.blindcafe.presentation.main.home.HomeState.PROFILE_ACCEPT
import com.abouttime.blindcafe.presentation.main.home.HomeState.PROFILE_OPEN
import com.abouttime.blindcafe.presentation.main.home.HomeState.PROFILE_READY
import com.abouttime.blindcafe.presentation.main.home.HomeState.WAIT
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeViewModel(
    private val getHomeInfoUseCase: GetHomeInfoUseCase,
    private val postMatchingRequestUseCase: PostMatchingRequestUseCase,
    private val getPartnerProfileUseCase: GetPartnerProfileUseCase,
    private val getChatRoomInfoUseCase: GetChatRoomInfoUseCase,

) : BaseViewModel() {
    private val _homeStatusCode: MutableLiveData<Int> = MutableLiveData<Int>(-1)
    val homeStatusCode: LiveData<Int> get() = _homeStatusCode
    private val _time: MutableLiveData<String> = MutableLiveData("00:00")
    val time: LiveData<String> get() = _time


    var reason: String? = null
    var partnerNickname: String? = null
    var matchingId: Int? = null
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
                    showLoading()
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
                    dismissLoading()
                }
                is Resource.Error -> {
                    Log.d(RETROFIT_TAG, resource.message.toString())
                    dismissLoading()
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getHomeInfoForNavigation(callback: (String) -> Unit) {
        getHomeInfoUseCase().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    resource.data?.matchingStatus?.let { status ->
                        callback(status)
                    }
                    dismissLoading()
                }
                is Resource.Error -> {
                    Log.d(RETROFIT_TAG, resource.message.toString())
                    dismissLoading()
                }
            }

        }.launchIn(viewModelScope)
    }


    private fun postMatchingRequest() {
        postMatchingRequestUseCase().onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    Log.d(RETROFIT_TAG, "Loading")
                    showLoading()
                }
                is Resource.Success -> {
                    Log.d(RETROFIT_TAG, response.data.toString())
                    response.data?.matchingStatus?.let { status ->
                        _homeStatusCode.postValue(getHomeStatusCode(status))
                    }
                    dismissLoading()
                }
                is Resource.Error -> {
                    Log.d(RETROFIT_TAG, response.message.toString())
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPartnerProfile() {
        matchingId?.let { id ->
            getPartnerProfileUseCase(id).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        result.data?.let { dto ->
                            handleGetPartnerProfileDto(dto)
                        }
                        dismissLoading()
                    }
                    is Resource.Error -> {
                        dismissLoading()
                    }
                }

            }.launchIn(viewModelScope)
        } ?: kotlin.run {
            showToast(R.string.temp_error)
        }
    }

    private fun getChatRoomInfo(matchingId: Int) {
        getChatRoomInfoUseCase(matchingId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.toChatRoom()?.let { cr ->
                        moveToChatFragment(cr)
                    }

                   dismissLoading()
                }
                is Resource.Error -> {
                    dismissLoading()
                }
            }
        }.launchIn(viewModelScope)
    }


    /** handler **/
    private fun handleGetPartnerProfileDto(dto: GetPartnerProfileDto) {
        if (dto.fill == true) {
            /** 나 공개한 상태인데 상대방 작성 했으니 '(내)수락' 화면으로 이동 */
            moveToExchangeAcceptFragment()

        } else {
            /** 나 공개한 상태인데 상대방 작성 안 했으니 '상대 작성 대기' 화면으로 이동 */
            partnerNickname?.let { nick ->
                moveToExchangeOpenWaitFragment(
                    partnerNickname = nick,
                    reason = "프로필 작성"
                )
            }
        }
    }



    /** mapping status **/
    private fun getHomeStatusCode(status: String): Int {
        return when (status) {
            NONE -> 0
            WAIT -> 1
            FOUND -> 2
            MATCHING -> 3
            PROFILE_OPEN -> 4
            PROFILE_READY -> 5
            PROFILE_ACCEPT -> 6
            MATCHING_CONTINUE -> 7
            FAILED_LEAVE_ROOM -> 8
            FAILED_REPORT -> 9
            FAILED_WONT_EXCHANGE -> 10
            else -> 0
        }

    }

    /** onClick **/
    fun onClickCircleImageView(v: View) {
        getHomeInfoForNavigation() { status ->
            val statusCode = getHomeStatusCode(status)
            _time.value = startTime?.toLong()?.secondToLapseForHome()
            when (statusCode) {
                -1 -> {
                    showToast(R.string.temp_error)
                }
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
                    matchingId?.let { id ->
                        getChatRoomInfo(id)
                    }
                }
                4 -> {
                    /** 3일 끝, 나 작성도 했는지 모름, '공개하기' 화면가서 확인해 볼거임 */
                    moveToExchangeOpenFragment()
                }
                5 -> {
                    /** 나 일단 프로필 작성만 완료한 상태  */
                    getPartnerProfile()
                }
                6 -> {
                    /** 나 수락한 상태(즉 상대방 작성 o)이니 '상대 수락 대기' 화면으로 이동 */
                    partnerNickname?.let {
                        moveToExchangeOpenWaitFragment(
                            partnerNickname = it,
                            reason = "수락 대기"
                        )
                    }
                }
                8,9,10 -> {

                }
                else -> {
                    showToast(R.string.toast_fail)
                }
            }
        }

    }


    /** navigation **/
    private fun moveToChatFragment(chatRoom: ChatRoom) {
        moveToDirections(MainFragmentDirections.actionMainFragmentToChatFragment(
            chatRoom
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

    fun moveToExitFragmentByReport(partnerNickname: String) {
        moveToDirections(MainFragmentDirections.actionMainFragmentToExitFragment(
            isAttacker = false,
            isReport = true,
            title = "%s님이 불편함을 느껴 대화를 종료했습니다.\n아쉽지만 새로운 손님과 또 다른 추억을 쌓으러 가보죠!".format(partnerNickname)
        ))
    }
    fun moveToExitFragmentByQuit(partnerNickname: String, reason: String) {
        moveToDirections(MainFragmentDirections.actionMainFragmentToExitFragment(
            isAttacker = false,
            isReport = false,
            title = "%s 님이[\"%s\"]라는 이유로 대화를 진행하지 못하게되었습니다.\n\n아쉽지만 새로운 손님과 또 다른 추억을 쌓을 수 있습니다.".format(
                partnerNickname,
                reason)
        ))
    }
    fun moveToExitFragmentByDismissProfileExchange(partnerNickname: String, reason: String) {
        moveToDirections(MainFragmentDirections.actionMainFragmentToExitFragment(
            isAttacker = false,
            isReport = false,
            title = "%s님이[\"%s\"]라는 이유로 대화를 진행하지 못하게되었습니다.\n\n아쉽지만 새로운 손님과 또 다른 추억을 쌓을 수 있습니다.".format(
                partnerNickname,
                reason)
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

    private fun moveToExchangeOpenFragment() {
        matchingId?.let { id ->
            moveToDirections(MainFragmentDirections.actionMainFragmentToExchangeOpenFragment(
                matchingId = id
            ))
        }
    }

    fun moveToExchangeCompleteFragment() {
        matchingId?.let { id ->
            moveToDirections(MainFragmentDirections.actionMainFragmentToExchangeCompleteFragment(id))
        }
    }


    private fun moveToExchangeAcceptFragment() {
        matchingId?.let {
            moveToDirections(MainFragmentDirections.actionMainFragmentToExchangeAcceptFragment(
                matchingId = it
            ))
        }

    }

    private fun moveToExchangeOpenWaitFragment(partnerNickname: String, reason: String) {
        moveToDirections(MainFragmentDirections.actionMainFragmentToExchangeWaitFragment(
            partnerNickname = partnerNickname,
            reason = reason
        ))

    }


}