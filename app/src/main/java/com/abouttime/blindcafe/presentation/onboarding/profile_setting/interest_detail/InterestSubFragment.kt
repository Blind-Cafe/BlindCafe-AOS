package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.LogTag.RETROFIT_TAG
import com.abouttime.blindcafe.databinding.FragmentInterestSubBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.koin.android.viewmodel.ext.android.viewModel

class InterestSubFragment : BaseFragment<InterestSubViewModel>(R.layout.fragment_interest_sub) {
    override val viewModel: InterestSubViewModel by viewModel()
    private var binding: FragmentInterestSubBinding? = null
    private val args: InterestSubFragmentArgs by navArgs()
    private val subInterestAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentInterestSubBinding = FragmentInterestSubBinding.bind(view)
        binding = fragmentInterestSubBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        initNavArgsData()
        initRecyclerView(fragmentInterestSubBinding)
        observeInterestData()


    }
    private fun initNavArgsData() {
        val i1 = args.i1
        val i2 = args.i2
        val i3 = args.i3

        viewModel.getInterest(i1, i2, i3)
    }

    private fun initRecyclerView(fragmentInterestSubBinding: FragmentInterestSubBinding) =
        with(fragmentInterestSubBinding) {
            rvSubInterestContainer.apply {
                adapter = subInterestAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

           //OverScrollDecoratorHelper.setUpOverScroll(rvSubInterestContainer, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        }

    private fun observeInterestData() {
        viewModel.interests.observe(viewLifecycleOwner) { list ->
            Log.e(RETROFIT_TAG, list.toString())
            subInterestAdapter.add(InterestSubRvItem(list[0], viewModel, 0))
            subInterestAdapter.add(InterestSubRvItem(list[1], viewModel, 1))
            subInterestAdapter.add(InterestSubRvItem(list[2], viewModel, 2))
            subInterestAdapter.notifyDataSetChanged()
        }
    }

}