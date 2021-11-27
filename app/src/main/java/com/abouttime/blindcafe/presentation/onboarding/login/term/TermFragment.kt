package com.abouttime.blindcafe.presentation.onboarding.login.term

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentTermBinding
import org.koin.android.viewmodel.ext.android.viewModel

class TermFragment: BaseFragment<TermViewModel>(R.layout.fragment_term) {
    override val viewModel: TermViewModel by viewModel()
    private var binding: FragmentTermBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentTermBinding = FragmentTermBinding.bind(view)
        binding = fragmentTermBinding
        initBackButton(fragmentTermBinding)
    }

    private fun initBackButton(fragmentTermBinding: FragmentTermBinding) = with(fragmentTermBinding) {
        ivBack.setOnClickListener {
            popOneDirections()
        }
    }
}