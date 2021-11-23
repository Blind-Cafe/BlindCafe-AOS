package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.data.server.dto.interest.Interest
import com.abouttime.blindcafe.databinding.FragmentInterestSubBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import org.koin.android.viewmodel.ext.android.viewModel

class InterestSubFragment : BaseFragment<InterestSubViewModel>(R.layout.fragment_interest_sub) {
    override val viewModel: InterestSubViewModel by viewModel()
    private var binding: FragmentInterestSubBinding? = null
    private val subInterestAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentInterestSubBinding = FragmentInterestSubBinding.bind(view)
        binding = fragmentInterestSubBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initRecyclerView(fragmentInterestSubBinding)
        observeInterestData()
    }

    override fun onResume() {
        super.onResume()

    }


    private fun initRecyclerView(fragmentInterestSubBinding: FragmentInterestSubBinding) =
        with(fragmentInterestSubBinding) {
            rvSubInterestContainer.apply {
                adapter = subInterestAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
            subInterestAdapter.add(InterestSubRvItem(
                Interest(
                    main = 1,
                    sub = listOf(
                        "1", "2", "3", "4", "5", "6", "7", "8", "9"
                    )
                ), viewModel!!, 0))
            subInterestAdapter.add(InterestSubRvItem(
                Interest(
                    main = 1,
                    sub = listOf(
                        "1", "2", "3", "4", "5", "6", "7", "8", "9"
                    )
                ), viewModel!!, 1))
            subInterestAdapter.add(InterestSubRvItem(Interest(
                main = 1,
                sub = listOf(
                    "1", "2", "3", "4", "5", "6", "7", "8", "9"
                )
            ), viewModel!!, 2))
        }

    private fun observeInterestData() {
        viewModel.interests.observe(viewLifecycleOwner) { list ->
            Log.e("zcxv", "옴!")
            subInterestAdapter.add(InterestSubRvItem(list[0], viewModel, 0))
            subInterestAdapter.add(InterestSubRvItem(list[1], viewModel, 1))
            subInterestAdapter.add(InterestSubRvItem(list[2], viewModel, 2))
            subInterestAdapter.notifyDataSetChanged()
        }
    }

}