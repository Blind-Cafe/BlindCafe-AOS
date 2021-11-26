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


        observeSavedNavigationData(fragmentProfileEditBinding)
        observeLocationData(fragmentProfileEditBinding)
    }

    private fun observeSavedNavigationData(fragmentProfileEditBinding: FragmentProfileEditBinding) = with(fragmentProfileEditBinding) {
        getNavigationResult(SELECT_LOCATION)?.observe(viewLifecycleOwner) { result ->
            Log.e("navigation", result)
            tvLocationValue.text = result
            etLocationValue.isGone = result.isNotEmpty()
            btNext.setBackgroundColor(getColorByResId(R.color.main))
        }
    }

    private fun observeLocationData(fragmentProfileEditBinding: FragmentProfileEditBinding) = with(fragmentProfileEditBinding) {
        viewModel?.location?.observe(viewLifecycleOwner) { location ->
            tvLocationValue.text = location
            etLocationValue.isGone = location.isNotEmpty()
            if (location.isEmpty()) {
                btNext.setBackgroundColor(getColorByResId(R.color.edit_profile_button_disabled))
            } else {
                btNext.setBackgroundColor(getColorByResId(R.color.main))
            }
        }
    }


}