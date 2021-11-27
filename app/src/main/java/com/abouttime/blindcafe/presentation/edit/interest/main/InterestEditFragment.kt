package com.abouttime.blindcafe.presentation.edit.interest.main

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentInterestEditBinding
import org.koin.android.viewmodel.ext.android.viewModel

class InterestEditFragment: BaseFragment<InterestEditViewModel>(R.layout.fragment_interest_edit) {
    override val viewModel: InterestEditViewModel by viewModel()
    private var binding: FragmentInterestEditBinding? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentInterestEditBinding = FragmentInterestEditBinding.bind(view)
        binding = fragmentInterestEditBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel


        initNextButton(fragmentInterestEditBinding)
        initInterestsButton(fragmentInterestEditBinding)
    }



    private fun initNextButton(fragmentInterestEditBinding: FragmentInterestEditBinding) {
        fragmentInterestEditBinding.tvNext.setOnClickListener {
            viewModel?.onClickNextButton()
        }
    }

    private fun initInterestsButton(fragmentInterestEditBinding: FragmentInterestEditBinding) =
        with(fragmentInterestEditBinding) {
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
            viewModel?.let { vm ->
                with(vm) {
                    for (i in icons.indices) {
                        icons[i].setOnClickListener {
                            if (selectedItemIdx.contains(i)) {
                                selectedItemIdx.remove(i)
                                icons[i].setColorFilter(getColorByResId(R.color.interest_icon_disabled))
                            } else {
                                if (selectedItemIdx.size < 3) {
                                    selectedItemIdx.add(i)
                                    icons[i].setColorFilter(getColorByResId(R.color.main))
                                } else {
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

        }
}