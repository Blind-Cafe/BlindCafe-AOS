package com.abouttime.blindcafe.presentation.main.my_page.setting.report_list

import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.data.server.dto.user_info.report.Report
import com.abouttime.blindcafe.databinding.RvItemReportBinding
import com.xwray.groupie.viewbinding.BindableItem


class ReportRvItem(
    private val report: Report
): BindableItem<RvItemReportBinding>() {
    override fun bind(viewBinding: RvItemReportBinding, position: Int) = with(viewBinding) {
        tvTarget.text = tvTarget.resources.getString(R.string.report_confirm_yes).format(report.target)
        tvReason.text = report.reason
        tvDate.text = report.date
    }

    override fun getLayout(): Int = R.layout.rv_item_report

    override fun initializeViewBinding(view: View): RvItemReportBinding =
        RvItemReportBinding.bind(view)
}