package com.abouttime.blindcafe.presentation.onboarding.profile_setting.essential_second

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.fragment.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentEssentialSecondBinding

class EssentialSecondFragment: BaseFragment(R.layout.fragment_essential_second) {
    private var binding: FragmentEssentialSecondBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentEssentialSecondBinding = FragmentEssentialSecondBinding.bind(view)
        binding = fragmentEssentialSecondBinding
        initNextButton(fragmentEssentialSecondBinding)
    }
    private fun initNextButton(fragmentEssentialSecondBinding: FragmentEssentialSecondBinding) {
        fragmentEssentialSecondBinding.tvNext.setOnClickListener {
            moveToInterestFragment()
        }
    }


    private fun moveToInterestFragment() {
        moveToDirections(EssentialSecondFragmentDirections.actionEssentialSecondFragmentToInterestFragment())
    }
}