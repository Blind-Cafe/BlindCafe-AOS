package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentInterestSubBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.viewModel

class InterestSubFragment: BaseFragment<InterestSubViewModel>(R.layout.fragment_interest_sub) {
    override val viewModel: InterestSubViewModel by viewModel()
    private var binding: FragmentInterestSubBinding? = null
    private val subInterestAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentInterestSubBinding = FragmentInterestSubBinding.bind(view)
        binding = fragmentInterestSubBinding

        initRecyclerView(fragmentInterestSubBinding)
    }

    override fun onResume() {
        super.onResume()
        addItems()
    }
    private fun initRecyclerView(fragmentInterestSubBinding: FragmentInterestSubBinding) = with(fragmentInterestSubBinding) {
        rvSubInterestContainer.apply {
            adapter = subInterestAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        Log.e("zcxv", "옴! $viewModel")

    }
    private fun addItems() {
        viewModel?.let {
            Log.e("zcxv", "옴!")
            subInterestAdapter.add(InterestSubRvItem(it, 0))
            subInterestAdapter.add(InterestSubRvItem(it, 1))
            subInterestAdapter.add(InterestSubRvItem(it, 2))
            subInterestAdapter.notifyDataSetChanged()
        }
    }
}