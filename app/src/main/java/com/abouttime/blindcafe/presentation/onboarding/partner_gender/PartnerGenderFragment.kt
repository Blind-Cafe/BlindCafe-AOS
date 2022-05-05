package com.abouttime.blindcafe.presentation.onboarding.partner_gender

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentGenderAgeBinding
import com.abouttime.blindcafe.databinding.FragmentPartnerGenderBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PartnerGenderFragment : BaseFragment<PartnerGenderViewModel>(R.layout.fragment_partner_gender) {
    override val viewModel: PartnerGenderViewModel by viewModel()
    private var binding: FragmentPartnerGenderBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentPartnerGenderBinding = FragmentPartnerGenderBinding.bind(view)
        binding = fragmentPartnerGenderBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        observeData(fragmentPartnerGenderBinding)
    }

    private fun observeData(fragmentPartnerGenderBinding: FragmentPartnerGenderBinding) = with(fragmentPartnerGenderBinding) {
        val sexDisabledColor = getColorByResId(R.color.disabled)
        val sexEnabledColor = getColorByResId(R.color.sex_enabled)
        viewModel?.selectedSex?.observe(viewLifecycleOwner) { selectedSex ->
            when(selectedSex) {
                1 -> {
                    ivFemale.setColorFilter(sexEnabledColor)
                    ivMale.setColorFilter(sexDisabledColor)
                    ivBisexual.setColorFilter(sexDisabledColor)
                }
                2 -> {
                    ivFemale.setColorFilter(sexDisabledColor)
                    ivMale.setColorFilter(sexEnabledColor)
                    ivBisexual.setColorFilter(sexDisabledColor)
                }
                3 -> {
                    ivFemale.setColorFilter(sexDisabledColor)
                    ivMale.setColorFilter(sexDisabledColor)
                    ivBisexual.setColorFilter(sexEnabledColor)
                }
                else -> {
                    ivFemale.setColorFilter(sexDisabledColor)
                    ivMale.setColorFilter(sexDisabledColor)
                    ivBisexual.setColorFilter(sexDisabledColor)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}