package com.abouttime.blindcafe.presentation.chat.report.confirm

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ReportDialogFragment: BaseDialogFragment<ReportViewModel>(R.layout.dialog_fragment_report) {
    override val viewModel: ReportViewModel by viewModel()
}