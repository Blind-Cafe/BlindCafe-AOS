package com.abouttime.blindcafe.presentation.chat.partner_profile

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentPartnerProfileBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PartnerProfileFragment: BaseFragment<PartnerProfileViewModel>(R.layout.fragment_partner_profile) {
    override val viewModel: PartnerProfileViewModel by viewModel()
    private var binding: FragmentPartnerProfileBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentPartnerProfileBinding = FragmentPartnerProfileBinding.bind(view)
        binding = fragmentPartnerProfileBinding


    }
}