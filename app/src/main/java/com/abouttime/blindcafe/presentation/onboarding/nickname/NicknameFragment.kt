package com.abouttime.blindcafe.presentation.onboarding.nickname

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentNicknameBinding
import org.koin.android.viewmodel.ext.android.viewModel

class NicknameFragment :
    BaseFragment<NicknameViewModel>(R.layout.fragment_nickname) {
    private var binding: FragmentNicknameBinding? = null
    override val viewModel: NicknameViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentNicknameBinding = FragmentNicknameBinding.bind(view)
        binding = fragmentNicknameBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel


        observeData(fragmentNicknameBinding)
        initAgeEditText(fragmentNicknameBinding)

    }

    private fun observeData(fragmentNicknameBinding: FragmentNicknameBinding) = with(fragmentNicknameBinding) {
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


    private fun initAgeEditText(fragmentNicknameBinding: FragmentNicknameBinding) {
        val nicknameEditText = fragmentNicknameBinding.etNickname
        val alertNicknameText1 = fragmentNicknameBinding.tvAlertNickname1
        val alertNicknameText2 = fragmentNicknameBinding.tvAlertNickname2

        nicknameEditText.setOnFocusChangeListener { view, isFocused ->
            nicknameEditText.isCursorVisible = isFocused
            if (isFocused) {
                view.setBackgroundResource(R.drawable.et_bg_rouding_with_green_stroke)
            } else {
                view.setBackgroundResource(R.drawable.et_bg_rounding_with_default_stroke)
            }
        }

        viewModel.nickNameText.observe(viewLifecycleOwner) {
            if (viewModel.isCorrectNickname()) {
                nicknameEditText.setBackgroundResource(R.drawable.et_bg_rouding_with_green_stroke)
                alertNicknameText1.setTextColor(getColorByResId(R.color.main))
                alertNicknameText2.setTextColor(getColorByResId(R.color.white))
            } else {
                nicknameEditText.setBackgroundResource(R.drawable.et_bg_rouding_with_red_stroke)
                alertNicknameText1.setTextColor(getColorByResId(R.color.alert_red))
                alertNicknameText2.setTextColor(getColorByResId(R.color.alert_red))

                showToast(R.string.profile_setting_toast_input_again_nickname)
            }

            viewModel.checkInputAll()
        }


    }


}