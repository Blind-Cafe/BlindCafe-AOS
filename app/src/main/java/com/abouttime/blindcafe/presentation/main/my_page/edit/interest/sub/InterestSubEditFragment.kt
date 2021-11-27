package com.abouttime.blindcafe.presentation.main.my_page.edit.interest.sub

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentInterestSubBinding
import com.abouttime.blindcafe.databinding.FragmentInterestSubEditBinding
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail.InterestSubRvItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.koin.android.viewmodel.ext.android.viewModel

class InterestSubEditFragment: BaseFragment<InterestSubEditViewModel>(R.layout.fragment_interest_sub_edit) {
    override val viewModel: InterestSubEditViewModel by viewModel()
    private var binding: FragmentInterestSubEditBinding? = null
    private val subInterestAdapter = GroupAdapter<GroupieViewHolder>()

    private val args: InterestSubEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentInterestSubEditBinding = FragmentInterestSubEditBinding.bind(view)
        binding = fragmentInterestSubEditBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initArgs()
        initRecyclerView(fragmentInterestSubEditBinding)
        observeInterestData()
    }

    private fun initArgs() {
        val i1 = args.mainInterest1
        val i2 = args.mainInterest2
        val i3 = args.mainInterest3
        viewModel.i1 = i1
        viewModel.i2 = i2
        viewModel.i3 = i3
        getSubInterests(i1, i2, i3)
    }

    private fun getSubInterests(i1: Int, i2: Int, i3: Int) {
        viewModel.getInterest(i1, i2, i3)
    }


    private fun initRecyclerView(fragmentInterestSubEditBinding: FragmentInterestSubEditBinding) =
        with(fragmentInterestSubEditBinding) {
            rvSubInterestContainer.apply {
                adapter = subInterestAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            //OverScrollDecoratorHelper.setUpOverScroll(rvSubInterestContainer, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        }

    private fun observeInterestData() {
        viewModel.interests.observe(viewLifecycleOwner) { list ->
            subInterestAdapter.add(InterestSubEditItem(list[0], viewModel, 0))
            subInterestAdapter.add(InterestSubEditItem(list[1], viewModel, 1))
            subInterestAdapter.add(InterestSubEditItem(list[2], viewModel, 2))
            subInterestAdapter.notifyDataSetChanged()
        }
    }
}