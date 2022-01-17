package com.abouttime.blindcafe.presentation.main.my_page.setting.report_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.util.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.data.remote.server.dto.user_info.report.Report
import com.abouttime.blindcafe.domain.use_case.server.GetReportsUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ReportListViewModel(
    private val getReportsUseCase: GetReportsUseCase
): BaseViewModel() {

    private val _reportList = SingleLiveData<List<Report>>()
    val reportList: SingleLiveData<List<Report>> get() = _reportList

    private val _isNone =  MutableLiveData(true)
    val isNone:LiveData<Boolean> get() = _isNone

    init {
        getReports()
    }


    /** use cases **/
    private fun getReports() {
        getReportsUseCase().onEach { result ->
            when(result) {
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    result.data?.reports?.let { list ->
                        _reportList.value = list
                        _isNone.value = list.isEmpty()
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

    fun onClickBackButton() {
        popDirections()
    }


}