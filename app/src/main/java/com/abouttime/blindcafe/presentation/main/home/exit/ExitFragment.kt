package com.abouttime.blindcafe.presentation.main.home.exit

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentExitBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ExitFragment: BaseFragment<ExitViewModel>(R.layout.fragment_exit) {
    override val viewModel: ExitViewModel by viewModel()
    private var binding: FragmentExitBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentExitBinding = FragmentExitBinding.bind(view)
        binding = fragmentExitBinding


    }


}