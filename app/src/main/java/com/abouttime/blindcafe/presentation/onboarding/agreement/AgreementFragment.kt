package com.abouttime.blindcafe.presentation.onboarding.agreement

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentAgreementBinding
import org.koin.android.viewmodel.ext.android.viewModel

class AgreementFragment : BaseFragment<AgreementViewModel>(R.layout.fragment_agreement) {
    private var binding: FragmentAgreementBinding? = null
    override val viewModel: AgreementViewModel by viewModel()

    private lateinit var checkImageViews: List<ImageView>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAgreementBinding = FragmentAgreementBinding.bind(view)
        binding = fragmentAgreementBinding
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this

    }



}