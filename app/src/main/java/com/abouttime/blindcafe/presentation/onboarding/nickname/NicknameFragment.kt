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

        initNicknameEditText(fragmentNicknameBinding)
    }


    private fun initNicknameEditText(fragmentNicknameBinding: FragmentNicknameBinding) {
        val nicknameEditText = fragmentNicknameBinding.etNickname
        val alertNicknameText1 = fragmentNicknameBinding.tvAlertNickname1
        val alertNicknameText2 = fragmentNicknameBinding.tvAlertNickname2

        nicknameEditText.setOnFocusChangeListener { view, isFocused ->
            nicknameEditText.isCursorVisible = isFocused
        }

        viewModel.nickNameText.observe(viewLifecycleOwner) {
            if (viewModel.isCorrectNickname()) {
                alertNicknameText1.setTextColor(getColorByResId(R.color.main))
                alertNicknameText2.setTextColor(getColorByResId(R.color.white))
            } else {
                alertNicknameText1.setTextColor(getColorByResId(R.color.alert_red))
                alertNicknameText2.setTextColor(getColorByResId(R.color.alert_red))
            }
            viewModel.checkInputAll()
        }
    }


}