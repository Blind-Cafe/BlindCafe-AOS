package com.abouttime.blindcafe.presentation.main.home

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.Url
import com.abouttime.blindcafe.databinding.FragmentHomeBinding
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment<HomeViewModel>(R.layout.fragment_home) {
    private var binding: FragmentHomeBinding? = null
    override val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this



    }

}