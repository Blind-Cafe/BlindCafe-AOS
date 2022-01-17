package com.abouttime.blindcafe.presentation.onboarding.signin

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentSigninBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SigninFragment: BaseFragment<SigninViewModel>(R.layout.fragment_signin) {
    override val viewModel: SigninViewModel by viewModel()
    private var binding: FragmentSigninBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentSigninBinding = FragmentSigninBinding.bind(view)
        binding = fragmentSigninBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
        initNavArgs()
    }
    private fun initNavArgs() {

    }

}