package com.abouttime.blindcafe.presentation.main.my_page.setting.report_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abouttime.blindcafe.databinding.RvItemInterestSubBinding
import com.abouttime.blindcafe.databinding.RvItemReportBinding
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail.InterestSubViewModel



class ReportListAdapter(
    private val viewModel: ReportListViewModel
): RecyclerView.Adapter<ReportListAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: RvItemReportBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindResource(data: ReportItem) {

        }

        fun bindView(data: ReportItem) {

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RvItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindResource(viewModel.reportList[position])
        holder.bindView(viewModel.reportList[position])
    }

    override fun getItemCount(): Int = viewModel.reportList.size


}