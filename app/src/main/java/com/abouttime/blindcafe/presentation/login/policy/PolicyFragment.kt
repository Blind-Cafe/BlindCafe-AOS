package com.abouttime.blindcafe.presentation.login.policy


import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentPolicyBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PolicyFragment: BaseFragment<PolicyViewModel>(R.layout.fragment_policy) {
    override val viewModel: PolicyViewModel by viewModel()
    private var binding: FragmentPolicyBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentPolicyBinding = FragmentPolicyBinding.bind(view)
        binding = fragmentPolicyBinding
        initBackButton(fragmentPolicyBinding)
    }
    private fun initBackButton(fragmentPolicyBinding: FragmentPolicyBinding) = with(fragmentPolicyBinding) {
        ivBack.setOnClickListener {
            popOneDirections()
        }
    }
}