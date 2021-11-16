package com.abouttime.blindcafe.presentation.chat.report.confirm

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseDialogFragment
import com.abouttime.blindcafe.databinding.DialogFragmentReportBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ReportDialogFragment: BaseDialogFragment<ReportViewModel>(R.layout.dialog_fragment_report) {
    override val viewModel: ReportViewModel by viewModel()
    private var binding: DialogFragmentReportBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialogFragmentReportBinding = DialogFragmentReportBinding.bind(view)
        binding = dialogFragmentReportBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

    }
}