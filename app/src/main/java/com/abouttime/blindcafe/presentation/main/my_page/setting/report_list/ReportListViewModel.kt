package com.abouttime.blindcafe.presentation.main.my_page.setting.report_list

import android.util.Log
import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.common.SingleLiveData
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.data.server.dto.user_info.report.Report
import com.abouttime.blindcafe.domain.use_case.GetReportsUseCase
import kotlinx.coroutines.flow.onEach

class ReportListViewModel(
    private val getReportsUseCase: GetReportsUseCase
): BaseViewModel() {

    private val _reportList = SingleLiveData<List<Report>>()
    val reportList: SingleLiveData<List<Report>> get() = _reportList

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
                    }
                    dismissLoading()
                }
                is Resource.Error -> {
                    Log.d(RETROFIT_TAG, result.message.toString())
                    dismissLoading()
                }
            }
        }
    }


}