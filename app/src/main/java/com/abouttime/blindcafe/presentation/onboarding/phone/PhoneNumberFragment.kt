package com.abouttime.blindcafe.presentation.onboarding.phone

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentPhoneNumberBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PhoneNumberFragment: BaseFragment<PhoneNumberViewModel>(R.layout.fragment_phone_number) {
    override val viewModel: PhoneNumberViewModel by viewModel()
    private lateinit var binding: FragmentPhoneNumberBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentPhoneNumberBinding = FragmentPhoneNumberBinding.bind(view)
        binding = fragmentPhoneNumberBinding

    }
}