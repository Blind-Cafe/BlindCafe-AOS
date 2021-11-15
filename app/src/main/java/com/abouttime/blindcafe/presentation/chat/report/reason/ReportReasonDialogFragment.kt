package com.abouttime.blindcafe.presentation.chat.report.reason

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ReportReasonDialogFragment: BaseDialogFragment<ReportReasonViewModel>(R.layout.dialog_fragment_report_reason) {
    override val viewModel: ReportReasonViewModel by viewModel()
}