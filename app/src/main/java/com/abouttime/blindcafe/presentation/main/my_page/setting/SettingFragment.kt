package com.abouttime.blindcafe.presentation.main.my_page.setting

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_ENTIRE
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_FALSE
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_MESSAGE
import com.abouttime.blindcafe.common.constants.PreferenceKey.NOTIFICATION_TRUE
import com.abouttime.blindcafe.databinding.FragmentSettingBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SettingFragment: BaseFragment<SettingViewModel>(R.layout.fragment_setting) {
    override val viewModel: SettingViewModel by viewModel()
    private var binding: FragmentSettingBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentSettingBinding = FragmentSettingBinding.bind(view)
        binding = fragmentSettingBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        initSwitchButton()
    }

    private fun initSwitchButton() {
        val isEntire = getStringData(NOTIFICATION_ENTIRE)
        val isMessage = getStringData(NOTIFICATION_MESSAGE)
        binding?.swNotificationEntire?.isChecked = (isEntire == null || isEntire == NOTIFICATION_TRUE)
        binding?.swNotificationMessage?.isChecked = (isMessage == null || isMessage == NOTIFICATION_TRUE)


        binding?.swNotificationEntire?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveStringData(Pair(
                    NOTIFICATION_ENTIRE,
                    NOTIFICATION_TRUE
                ))
            } else {
                saveStringData(Pair(
                    NOTIFICATION_ENTIRE,
                    NOTIFICATION_FALSE
                ))
            }
        }
        binding?.swNotificationMessage?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveStringData(Pair(
                    NOTIFICATION_MESSAGE,
                    NOTIFICATION_TRUE
                ))
            } else {
                saveStringData(Pair(
                    NOTIFICATION_MESSAGE,
                    NOTIFICATION_FALSE
                ))
            }
        }
    }


}