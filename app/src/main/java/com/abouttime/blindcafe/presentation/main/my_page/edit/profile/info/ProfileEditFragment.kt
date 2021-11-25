package com.abouttime.blindcafe.presentation.main.my_page.edit.profile.info

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
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

    }


}