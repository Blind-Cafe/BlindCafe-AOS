package com.abouttime.blindcafe.presentation.onboarding.gender_age

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentGenderAgeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class GenderAgeFragment :
    BaseFragment<GenderAgeViewModel>(R.layout.fragment_gender_age) {

    private var binding: FragmentGenderAgeBinding? = null
    override val viewModel: GenderAgeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentGenderAgeBinding = FragmentGenderAgeBinding.bind(view)
        binding = fragmentGenderAgeBinding
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this

        initAgeEditText(fragmentGenderAgeBinding)
        observeData(fragmentGenderAgeBinding)
    }

    private fun observeData(fragmentGenderAgeBinding: FragmentGenderAgeBinding) = with(fragmentGenderAgeBinding) {
        val sexDisabledColor = getColorByResId(R.color.disabled)
        val sexEnabledColor = getColorByResId(R.color.sex_enabled)
        viewModel?.selectedSex?.observe(viewLifecycleOwner) { selectedSex ->
            when(selectedSex) {
                1 -> {
                    ivFemale.setColorFilter(sexEnabledColor)
                    ivMale.setColorFilter(sexDisabledColor)
                }
                2 -> {
                    ivFemale.setColorFilter(sexDisabledColor)
                    ivMale.setColorFilter(sexEnabledColor)
                }
                else -> {
                    ivFemale.setColorFilter(sexDisabledColor)
                    ivMale.setColorFilter(sexDisabledColor)
                }
            }

        }
    }


    private fun initAgeEditText(fragmentGenderAgeBinding: FragmentGenderAgeBinding) {
        val ageEditText = fragmentGenderAgeBinding.etAge
        val alertAgeTextView = fragmentGenderAgeBinding.tvAlertAge

        ageEditText.setOnFocusChangeListener { view, isFocused ->
            ageEditText.isCursorVisible = isFocused
        }

        viewModel.ageText.observe(viewLifecycleOwner) { input ->
            if (input.length < 2) return@observe

            if (viewModel.isCorrectAge()) {
                hideKeyboard()
                ageEditText.clearFocus()
                alertAgeTextView.isGone = true
            } else {
                alertAgeTextView.isGone = false
            }
            viewModel.checkInputAll()
        }


    }



}