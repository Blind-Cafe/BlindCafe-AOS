package com.abouttime.blindcafe.presentation.onboarding.profile_setting.essential_second

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentEssentialFirstBinding
import com.abouttime.blindcafe.databinding.FragmentEssentialSecondBinding
import org.koin.android.viewmodel.ext.android.viewModel

class EssentialSecondFragment :
    BaseFragment<EssentialSecondViewModel>(R.layout.fragment_essential_second) {
    private var binding: FragmentEssentialSecondBinding? = null
    override val viewModel: EssentialSecondViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentEssentialSecondBinding = FragmentEssentialSecondBinding.bind(view)
        binding = fragmentEssentialSecondBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel


        observeData(fragmentEssentialSecondBinding)
        initAgeEditText(fragmentEssentialSecondBinding)

    }

    private fun observeData(fragmentEssentialSecondBinding: FragmentEssentialSecondBinding) = with(fragmentEssentialSecondBinding) {
        val sexDisabledColor = getColor(R.color.sex_disabled)
        val sexEnabledColor = getColor(R.color.sex_enabled)
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


    private fun initAgeEditText(fragmentEssentialSecondBinding: FragmentEssentialSecondBinding) {
        val nicknameEditText = fragmentEssentialSecondBinding.etNickname
        val alertNicknameText = fragmentEssentialSecondBinding.tvAlertNickname

        nicknameEditText.setOnFocusChangeListener { view, isFocused ->
            if (isFocused) {
                view.setBackgroundResource(R.drawable.et_bg_rouding_with_green_stroke)
            } else {
                view.setBackgroundResource(R.drawable.et_bg_rounding_with_default_stroke)
            }
        }

        viewModel.nickNameText.observe(viewLifecycleOwner) {
            if (viewModel.isCorrectNickname()) {
                nicknameEditText.setBackgroundResource(R.drawable.et_bg_rouding_with_green_stroke)
                alertNicknameText.setTextColor(resources.getColor(R.color.white, null))
            } else {
                nicknameEditText.setBackgroundResource(R.drawable.et_bg_rouding_with_red_stroke)
                alertNicknameText.setTextColor(resources.getColor(R.color.alert_red, null))
                showToast(R.string.profile_setting_toast_input_again_nickname)
            }

            viewModel.checkInputAll()
        }


    }


}