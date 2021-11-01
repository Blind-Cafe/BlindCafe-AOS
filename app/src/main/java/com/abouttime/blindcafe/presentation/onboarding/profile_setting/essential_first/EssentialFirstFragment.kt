package com.abouttime.blindcafe.presentation.onboarding.profile_setting.essential_first

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.fragment.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentEssentialFirstBinding

class EssentialFirstFragment : BaseFragment(R.layout.fragment_essential_first) {

    private var binding: FragmentEssentialFirstBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentEssentialFirstBinding = FragmentEssentialFirstBinding.bind(view)
        binding = fragmentEssentialFirstBinding

        fragmentEssentialFirstBinding.ivFemale.setColorFilter(resources.getColor(R.color.main, null))

        initNextButton(fragmentEssentialFirstBinding)
    }

    private fun initNextButton(fragmentEssentialFirstBinding: FragmentEssentialFirstBinding) =
        with(fragmentEssentialFirstBinding) {
            tvNext.setOnClickListener {
                moveToEssentialSecondFragment()
            }
        }


    private fun moveToEssentialSecondFragment() {
        moveToDirections(EssentialFirstFragmentDirections.actionProfileSettingFragmentToEssentialSecondFragment())
    }

}