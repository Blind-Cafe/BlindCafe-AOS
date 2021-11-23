package com.abouttime.blindcafe.presentation.chat.report

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.data.server.dto.matching.report.PostReportDto
import com.abouttime.blindcafe.domain.use_case.PostReportUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ReportReasonViewModel(
    private val postReportUseCase: PostReportUseCase
) : BaseViewModel() {
    private val _reason = MutableLiveData(0)
    val reason: LiveData<Int> get() = _reason

    var matchingId: Int? = null



    /** use cases **/
    fun postReport(v: View, matchingId: Int, reason: Int) {
            postReportUseCase(PostReportDto(
                matchingId = matchingId,
                reason =  reason
            )).onEach { result ->
                when (result) {
                    is Resource.Loading -> { showLoading() }
                    is Resource.Success -> {
                        Log.e(RETROFIT_TAG, result.data.toString())
                        moveToConfirmDialogFragment(v, matchingId, reason)
                        dismissLoading()
                    }
                    is Resource.Error -> {
                        Log.e(RETROFIT_TAG, result.message.toString())
                        showToast(R.string.toast_fail)
                        dismissLoading()
                    }
                }
            }.launchIn(viewModelScope)
    }


    /** onClick **/
    fun onClickYesButton(v: View) {
        if (canClickNextButton()) {
            reason.value?.let { r ->
                matchingId?.let { m ->
                    postReport(v, m, r)
                } ?: kotlin.run {
                    showToast(R.string.toast_fail)
                }
            }
        } else {
            showToast(R.string.toast_select_reason)
        }

    }

    fun onClickNoButton() {
        popDirections()
    }

    fun onClickCheckButton(view: View) {
        when (view.id) {
            R.id.iv_check_1 -> _reason.value = 1
            R.id.iv_check_2 -> _reason.value = 2
            R.id.iv_check_3 -> _reason.value = 3
            R.id.iv_check_4 -> _reason.value = 4
            R.id.iv_check_5 -> _reason.value = 5
        }
    }


    fun moveToConfirmDialogFragment(v: View, matchingId: Int, reason: Int) {
        moveToDirections(
            ReportReasonDialogFragmentDirections.actionReportReasonDialogFragmentToConfirmDialogFragment(
                id = R.string.report_confirm_title,
                title = v.resources.getString(R.string.report_confirm_title),
                subtitle = v.resources.getString(R.string.report_confirm_subtitle),
                no = v.resources.getString(R.string.report_confirm_no),
                yes = v.resources.getString(R.string.report_confirm_yes),
                reason = reason,
                matchingId = matchingId
            )
        )
    }

    fun canClickNextButton() = _reason.value != 0


}