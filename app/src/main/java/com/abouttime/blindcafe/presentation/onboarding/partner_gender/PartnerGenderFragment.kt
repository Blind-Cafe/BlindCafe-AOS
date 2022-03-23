package com.abouttime.blindcafe.presentation.onboarding.partner_gender

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentPartnerGenderBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PartnerGenderFragment : BaseFragment<PartnerGenderViewModel>(R.layout.fragment_partner_gender) {
    override val viewModel: PartnerGenderViewModel by viewModel()
    private var binding: FragmentPartnerGenderBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentInterestSubBinding = FragmentPartnerGenderBinding.bind(view)
        binding = fragmentInterestSubBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel


    }

}