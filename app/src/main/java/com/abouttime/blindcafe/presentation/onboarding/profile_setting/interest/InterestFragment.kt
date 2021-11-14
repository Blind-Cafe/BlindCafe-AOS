package com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentInterestBinding
import org.koin.android.viewmodel.ext.android.viewModel

class InterestFragment : BaseFragment<InterestViewModel>(R.layout.fragment_interest) {
    private var binding: FragmentInterestBinding? = null
    override val viewModel: InterestViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentInterestBinding = FragmentInterestBinding.bind(view)
        binding = fragmentInterestBinding

        //initRecyclerView(fragmentInterestBinding)
        initNextButton(fragmentInterestBinding)
        initInterestsButton(fragmentInterestBinding)
    }

    private fun initRecyclerView(fragmentInterestBinding: FragmentInterestBinding) =
        with(fragmentInterestBinding) {
            /*
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
             */
        }

    private fun initNextButton(fragmentInterestBinding: FragmentInterestBinding) {
        fragmentInterestBinding.tvNext.setOnClickListener {
            viewModel.onClickNextButton()
        }
    }

    private fun initInterestsButton(fragmentInterestBinding: FragmentInterestBinding) =
        with(fragmentInterestBinding) {
            val containers = arrayOf(
                flInterestContainer1,
                flInterestContainer2,
                flInterestContainer3,
                flInterestContainer4,
                flInterestContainer5,
                flInterestContainer6,
                flInterestContainer7,
                flInterestContainer8,
                flInterestContainer9
            )
            val icons = arrayOf(
                ivInterest1,
                ivInterest2,
                ivInterest3,
                ivInterest4,
                ivInterest5,
                ivInterest6,
                ivInterest7,
                ivInterest8,
                ivInterest9,
            )
            with(viewModel) {
                for (i in containers.indices) {
                    containers[i].setOnClickListener {
                        if (selectedItemIdx.contains(i)) {
                            selectedItemIdx.remove(i)
                            containers[i].setBackgroundResource(R.drawable.bg_interest_disabled)
                            icons[i].setColorFilter(getColorByResId(R.color.interest_icon_disabled))
                        } else {
                            if (selectedItemIdx.size < 3) {
                                selectedItemIdx.add(i)
                                containers[i].setBackgroundResource(R.drawable.bg_interest_enabled)
                                icons[i].setColorFilter(getColorByResId(R.color.interest_icon_enabled))
                            }
                        }

                        if (canEnableNextButton()) {
                            tvNext.setBackgroundResource(R.color.main)
                        } else {
                            tvNext.setBackgroundResource(R.color.button_disabled)
                        }

                    }
                }
            }
        }

}