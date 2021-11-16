package com.abouttime.blindcafe.presentation.chat.report.reason

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseDialogFragment
import com.abouttime.blindcafe.databinding.DialogFragmentReportReasonBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ReportReasonDialogFragment: BaseDialogFragment<ReportReasonViewModel>(R.layout.dialog_fragment_report_reason) {
    override val viewModel: ReportReasonViewModel by viewModel()
    private var binding: DialogFragmentReportReasonBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialogFragmentReportReasonBinding = DialogFragmentReportReasonBinding.bind(view)
        binding = dialogFragmentReportReasonBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

    }
}