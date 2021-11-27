package com.abouttime.blindcafe.presentation.profile_exchange.open

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.NavigationKey
import com.abouttime.blindcafe.databinding.FragmentExchangeOpenBinding
import com.abouttime.blindcafe.databinding.FragmentProfileEditBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ExchangeOpenFragment: BaseFragment<ExchangeOpenViewModel>(R.layout.fragment_exchange_open) {
    override val viewModel: ExchangeOpenViewModel by viewModel()
    private var binding: FragmentExchangeOpenBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentExchangeOpenBinding = FragmentExchangeOpenBinding.bind(view)
        binding = fragmentExchangeOpenBinding

        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initNicknameEditText(fragmentExchangeOpenBinding)

        observeSavedNavigationData(fragmentExchangeOpenBinding)
        observeLocationData(fragmentExchangeOpenBinding)
        observeNicknameData(fragmentExchangeOpenBinding)

    }





    private fun initNicknameEditText(fragmentExchangeOpenBinding: FragmentExchangeOpenBinding) = with(fragmentExchangeOpenBinding) {
        etNicknameValue.setOnFocusChangeListener { v, hasFocus ->
            etNicknameValue.isCursorVisible = hasFocus
        }
    }

    private fun observeSavedNavigationData(fragmentProfileEditBinding: FragmentExchangeOpenBinding) = with(fragmentProfileEditBinding) {
        getNavigationResult(NavigationKey.SELECT_LOCATION)?.observe(viewLifecycleOwner) { result ->
            Log.e("location -> profile edit", result)
            viewModel?.setLocation(result)

        }
    }
    private fun observeEnableData(fragmentExchangeOpenBinding: FragmentExchangeOpenBinding) = with(fragmentExchangeOpenBinding) {
        viewModel?.canEnableNext?.observe(viewLifecycleOwner) { isEnable ->
            if (isEnable) {
                tvNext.setBackgroundColor(getColorByResId(R.color.edit_profile_button_disabled))
            } else {
                tvNext.setBackgroundColor(getColorByResId(R.color.main))
            }
        }
    }
    private fun observeNicknameData(fragmentExchangeOpenBinding: FragmentExchangeOpenBinding) = with(fragmentExchangeOpenBinding) {
        viewModel?.nickname?.observe(viewLifecycleOwner) { nick ->
            viewModel?.updateNextButton()
            Log.e("nickname", nick)
            if (nick.length in 1..9) {
                tvNicknameRule.setTextColor(getColorByResId(R.color.gray_300))
                vNicknameDivider.setBackgroundColor(getColorByResId(R.color.white))
            } else {
                tvNicknameRule.setTextColor(getColorByResId(R.color.alert_red))
                vNicknameDivider.setBackgroundColor(getColorByResId(R.color.alert_red))
            }

        }
    }

    private fun observeLocationData(fragmentExchangeOpenBinding: FragmentExchangeOpenBinding) = with(fragmentExchangeOpenBinding) {
        viewModel?.location?.observe(viewLifecycleOwner) { loc ->
            Log.e("profile edit", loc)
            tvLocationValue.text = loc
            etLocationValue.isGone = loc.isNotEmpty()
            viewModel?.updateNextButton()
        }
    }

}