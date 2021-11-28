package com.abouttime.blindcafe.presentation.main.chat_list.matching

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentMatchingBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MatchingFragment: BaseFragment<MatchingViewModel>(R.layout.fragment_matching) {
    override val viewModel: MatchingViewModel by viewModel()
    private var binding: FragmentMatchingBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentMatchingBinding = FragmentMatchingBinding.bind(view)
        binding = fragmentMatchingBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel
    }
}