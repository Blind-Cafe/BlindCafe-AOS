package com.abouttime.blindcafe.presentation.main.my_page.setting.report_list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentReportListBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.viewModel

class ReportListFragment: BaseFragment<ReportListViewModel>(R.layout.fragment_report_list) {
    override val viewModel: ReportListViewModel by viewModel()
    private var binding: FragmentReportListBinding? = null
    private val reportAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentReportListBinding = FragmentReportListBinding.bind(view)
        binding = fragmentReportListBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initRecyclerView(fragmentReportListBinding)
        observeReportsData()
    }

    private fun initRecyclerView(fragmentReportListBinding: FragmentReportListBinding) = with(fragmentReportListBinding) {
        rvReportList.apply {
            adapter = reportAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeReportsData() {
        viewModel.reportList.observe(viewLifecycleOwner) { list ->
            reportAdapter.clear()
            list.forEach { report ->
                reportAdapter.add(ReportRvItem(report))
            }
        }
    }
}