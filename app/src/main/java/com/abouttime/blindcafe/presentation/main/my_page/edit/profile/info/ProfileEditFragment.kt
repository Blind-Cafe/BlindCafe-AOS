package com.abouttime.blindcafe.presentation.main.my_page.edit.profile.info

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.NavigationKey.SELECT_LOCATION
import com.abouttime.blindcafe.databinding.FragmentProfileEditBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileEditFragment: BaseFragment<ProfileEditViewModel>(R.layout.fragment_profile_edit) {
    override val viewModel: ProfileEditViewModel by viewModel()
    private var binding: FragmentProfileEditBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentProfileEditBinding = FragmentProfileEditBinding.bind(view)
        binding = fragmentProfileEditBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initNicknameEditText(fragmentProfileEditBinding)

        observeSavedNavigationData(fragmentProfileEditBinding)
        observeLocationData(fragmentProfileEditBinding)
        observeNicknameData(fragmentProfileEditBinding)
        //observeEnableData(fragmentProfileEditBinding)
    }
    private fun initNicknameEditText(fragmentProfileEditBinding: FragmentProfileEditBinding) = with(fragmentProfileEditBinding) {
        etNicknameValue.setOnFocusChangeListener { v, hasFocus ->
            etNicknameValue.isCursorVisible = hasFocus
        }
    }

    private fun observeSavedNavigationData(fragmentProfileEditBinding: FragmentProfileEditBinding) = with(fragmentProfileEditBinding) {
        getNavigationResult(SELECT_LOCATION)?.observe(viewLifecycleOwner) { result ->
            Log.e("location -> profile edit", result)
            viewModel?.setLocation(result)

        }
    }
    private fun observeEnableData(fragmentProfileEditBinding: FragmentProfileEditBinding) = with(fragmentProfileEditBinding) {
        viewModel?.canEnableNext?.observe(viewLifecycleOwner) { isEnable ->
            if (isEnable) {
                tvNext.setBackgroundColor(getColorByResId(R.color.edit_profile_button_disabled))
            } else {
                tvNext.setBackgroundColor(getColorByResId(R.color.main))
            }
        }
    }
    private fun observeNicknameData(fragmentProfileEditBinding: FragmentProfileEditBinding) = with(fragmentProfileEditBinding) {
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

    private fun observeLocationData(fragmentProfileEditBinding: FragmentProfileEditBinding) = with(fragmentProfileEditBinding) {
       viewModel?.location?.observe(viewLifecycleOwner) { loc ->
           Log.e("profile edit", loc)
           tvLocationValue.text = loc
           etLocationValue.isGone = loc.isNotEmpty()
           viewModel?.updateNextButton()
       }
    }


}