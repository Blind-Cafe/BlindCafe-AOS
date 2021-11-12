package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.RvGridDecoration
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentInterestBinding
import org.koin.android.viewmodel.ext.android.viewModel

class InterestFragment : BaseFragment<InterestViewModel>(R.layout.fragment_interest) {
    private var binding: FragmentInterestBinding? = null
    private lateinit var interestRvAdapter: InterestRvAdapter
    override val viewModel: InterestViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentInterestBinding = FragmentInterestBinding.bind(view)
        binding = fragmentInterestBinding

        initRecyclerView(fragmentInterestBinding)
        initNextButton(fragmentInterestBinding)
    }

    private fun initRecyclerView(fragmentInterestBinding: FragmentInterestBinding) =
        with(fragmentInterestBinding) {
            interestRvAdapter = InterestRvAdapter(
                requireContext(),
                viewModel = viewModel,
                isClickedThreeItem = { isClickedThreeItem ->
                    setNextTextViewBackgroundColor(isClickedThreeItem, fragmentInterestBinding)
                }
            )
            fragmentInterestBinding.rvInterest.apply {
                adapter = interestRvAdapter
                layoutManager = GridLayoutManager(requireContext(), 3)

                addItemDecoration(RvGridDecoration(3, 30, true))
            }
        }

    private fun initNextButton(fragmentInterestBinding: FragmentInterestBinding) {
        fragmentInterestBinding.tvNext.setOnClickListener {
            viewModel.onClickNextButton()
        }
    }

    private fun moveToInterestDetailFragment() {
        viewModel.moveToDirections(InterestFragmentDirections.actionInterestFragmentToMainFragment())
    }

    private fun setNextTextViewBackgroundColor(
        isClickedThreeItem: Boolean,
        fragmentInterestBinding: FragmentInterestBinding,
    ) {
        if (isClickedThreeItem) {
            fragmentInterestBinding.tvNext.setBackgroundColor(resources.getColor(R.color.button_enabled,
                null))
        } else {
            fragmentInterestBinding.tvNext.setBackgroundColor(resources.getColor(R.color.button_disabled,
                null))
        }
    }
}