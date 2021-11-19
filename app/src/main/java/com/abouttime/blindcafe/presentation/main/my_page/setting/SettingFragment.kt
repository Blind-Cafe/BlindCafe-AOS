package com.abouttime.blindcafe.presentation.main.my_page.setting

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentSettingBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SettingFragment: BaseFragment<SettingViewModel>(R.layout.fragment_setting) {
    override val viewModel: SettingViewModel by viewModel()
    private var binding: FragmentSettingBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentSettingBinding = FragmentSettingBinding.bind(view)
        binding = fragmentSettingBinding

    }


}