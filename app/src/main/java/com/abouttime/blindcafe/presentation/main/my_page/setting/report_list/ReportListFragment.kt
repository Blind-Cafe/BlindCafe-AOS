package com.abouttime.blindcafe.presentation.main.my_page.setting.report_list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentReportListBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ReportListFragment: BaseFragment<ReportListViewModel>(R.layout.fragment_report_list) {
    override val viewModel: ReportListViewModel by viewModel()
    private var binding: FragmentReportListBinding? = null
    private lateinit var reportAdapter: ReportListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentReportListBinding = FragmentReportListBinding.bind(view)
        binding = fragmentReportListBinding

        initRecyclerView(fragmentReportListBinding)
    }

    private fun initRecyclerView(fragmentReportListBinding: FragmentReportListBinding) = with(fragmentReportListBinding) {
        rvReportList.apply {
            adapter = reportAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}