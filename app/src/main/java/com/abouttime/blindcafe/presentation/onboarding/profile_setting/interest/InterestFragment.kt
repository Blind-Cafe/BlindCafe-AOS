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
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        observeSelectedInterestIdxData(fragmentInterestBinding)
    }

    private fun observeSelectedInterestIdxData(fragmentInterestBinding: FragmentInterestBinding) =
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
            viewModel?.selectedItem?.observe(viewLifecycleOwner) { selectedItemIdx ->

                for (i in 0..8) {
                    if (selectedItemIdx.contains(i)) {
                        containers[i].setBackgroundResource(R.drawable.bg_interest_enabled)
                        icons[i].setColorFilter(getColorByResId(R.color.interest_icon_enabled))
                    } else {
                        containers[i].setBackgroundResource(R.drawable.bg_interest_disabled)
                        icons[i].setColorFilter(getColorByResId(R.color.interest_icon_disabled))
                    }
                }
                if (viewModel?.canEnableNextButton() == true) {
                    tvNext.setBackgroundResource(R.color.main)
                } else {
                    tvNext.setBackgroundResource(R.color.button_disabled)
                }
            }
        }


/*
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
                                icons[i].setColorFilter(getColorByResId(R.color.interest_icon_enabled) else {
                                showToast(R.string.interest_toast_up_to_3)
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


 */

}