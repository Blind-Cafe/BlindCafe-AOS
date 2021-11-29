package com.abouttime.blindcafe.presentation.profile_exchange.dismiss

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseViewModel

class ExchangeDismissViewModel: BaseViewModel() {

    private val _reason = MutableLiveData(0)
    val reason: LiveData<Int> get() = _reason

    var matchingId: Int? = null
    var partnerNickname: String? = null
    var startTime: Int? = null

    fun onClickCheckButton(view: View) {
        when(view.id) {
            R.id.iv_check_1 -> _reason.value = 1
            R.id.iv_check_2 -> _reason.value = 2
            R.id.iv_check_3 -> _reason.value = 3
            R.id.iv_check_4 -> _reason.value = 4
            R.id.iv_check_5 -> _reason.value = 5
        }
    }

    fun onClickNextButton(v: View) {
        if (_reason.value != 0) {
            matchingId?.let { id ->
                _reason.value?.let { r ->
                    partnerNickname?.let { nick ->
                        startTime?.let { time ->
                            moveToDirections(
                                ExchangeDismissFragmentDirections.actionExchangeDismissFragmentToConfirmDialogFragment(
                                    id = R.string.profile_dismiss_confirm_title,
                                    title = v.resources.getString(R.string.profile_dismiss_confirm_title),
                                    subtitle = v.resources.getString(R.string.profile_dismiss_confirm_subtitle).format(nick),
                                    no = v.resources.getString(R.string.profile_dismiss_confirm_no),
                                    yes = v.resources.getString(R.string.profile_dismiss_confirm_yes),
                                    matchingId = id,
                                    reason = r,
                                    nickname = nick,
                                    startTime = time
                                )
                            )
                        }

                    }
                }

            }

        } else {
            showToast(R.string.toast_input_reason)
        }

    }
}