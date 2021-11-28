package com.abouttime.blindcafe.presentation.main.home.coffee

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.common.constants.PreferenceKey.NICKNAME
import com.abouttime.blindcafe.data.server.dto.matching.PostDrinkDto
import com.abouttime.blindcafe.domain.model.Message
import com.abouttime.blindcafe.domain.use_case.firebase.SendMessageUseCase
import com.abouttime.blindcafe.domain.use_case.server.GetChatRoomInfoUseCase
import com.abouttime.blindcafe.domain.use_case.server.PostDrinkUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CoffeeOrderViewModel(
    private val postDrinkUseCase: PostDrinkUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val getChatRoomInfoUseCase: GetChatRoomInfoUseCase
): BaseViewModel() {
    private val _nextButton: MutableLiveData<Boolean> = MutableLiveData(false)
    val nextButton: LiveData<Boolean> get() = _nextButton


    var myNickname = getStringData(NICKNAME)

    /** room info **/
    var matchingId: Int? = null
    var partnerNickname: String? = null
    var profileImage: String? = null
    var drink: String? = null
    var commonInterest: String? = null
    var startTime: String? = null
    var interest: String? = null


    var currentSelect: Int = -1

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

    fun mapToDrinkName(idx: Int): String {
        return when (idx) {
            1 -> "아메리카노"
            2 -> "카페라떼"
            3 -> "카페모카"
            4 -> "버블티"
            5 -> "민트초코"
            6 -> "딸기 스무디"
            7 -> "블루 레몬 에이드"
            8 -> "녹차"
            9 -> "자몽티"
            else -> ""
        }
    }


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
                            }
                            matchingId?.let { roomUid ->
                                getChatRoomInfo(roomUid)
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

    fun getChatRoomInfo(mId: Int) {
        getChatRoomInfoUseCase(mId).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    result.data?.let { dto ->
                        matchingId?.let { mId ->
                            Log.e("drink", "${dto.drink} 0")

                            Log.e("drink", "${dto.drink} 1")
                            if (dto.drink == "미입력") {

                                Log.e("drink", "${dto.drink} 2")
                                sendDescriptionMessage(
                                    Message(
                                        contents = "매칭에 성공하였습니다.\n간단한 인사로 반갑게 맞아주세요",
                                        roomUid = mId.toString(),
                                        type = 7
                                    )
                                )
                                Log.e("drink", "${dto.drink} 3")
                                sendDescriptionMessage(
                                    Message(
                                        contents = "%s님과 %s님의 공통 관심사는 %s 입니다.".format(
                                            myNickname,
                                            partnerNickname,
                                            dto.interest
                                        ),
                                        roomUid = mId.toString(),
                                        type = 7
                                    )
                                )
                                Log.e("drink", "${dto.drink} 4")
                            }
                        }
                        this@CoffeeOrderViewModel.matchingId = dto.matchingId
                        this@CoffeeOrderViewModel.profileImage = dto.profileImage
                        this@CoffeeOrderViewModel.drink = dto.drink
                        this@CoffeeOrderViewModel.commonInterest = interest
                        this@CoffeeOrderViewModel.startTime = dto.startTime
                        this@CoffeeOrderViewModel.interest = dto.interest

                    }


                    matchingId?.let { id ->
                        sendDescriptionMessage(
                            Message(
                                contents = "%s님은 %s을(를) 주문하셨습니다.".format(
                                    myNickname,
                                    mapToDrinkName(currentSelect)
                                ),
                                type = 7,
                                roomUid = id.toString()
                            )
                        )


                        startTime?.let { time ->
                            partnerNickname?.let { pn ->
                                moveToChatFragment(
                                    matchingId = mId,
                                    startTime = time,
                                    partnerNickname = pn
                                )
                            }
                        }

                    }

                }
                is Resource.Error -> {
                }
            }

        }.launchIn(viewModelScope)

    }

    fun sendDescriptionMessage(message: Message) = viewModelScope.launch(Dispatchers.IO) {
        sendMessageUseCase(message).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d(LogTag.FIRESTORE_TAG, "Loading")
                }
                is Resource.Success -> {
                    Log.d(LogTag.FIRESTORE_TAG, "${result.data?.id}")
                }
                is Resource.Error -> {
                    Log.d(LogTag.FIRESTORE_TAG, "Error")
                }
            }

        }.launchIn(viewModelScope)
    }


    fun onClickNextButton() {
        if (canClickNextButton()) {
            postDrink()
        } else {
            showToast(R.string.coffee_order_next_alert)
        }
    }


        private fun moveToChatFragment(matchingId: Int, startTime: String, partnerNickname: String) {
        moveToDirections(CoffeeOrderFragmentDirections.actionCoffeeOrderFragmentToChatFragment(
            matchingId = matchingId,
            startTime = startTime,
            partnerNickname = partnerNickname
        ))
    }


}