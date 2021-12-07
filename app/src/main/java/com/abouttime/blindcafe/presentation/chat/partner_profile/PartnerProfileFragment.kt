package com.abouttime.blindcafe.presentation.chat.partner_profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentPartnerProfileBinding
import org.koin.android.viewmodel.ext.android.viewModel

// TODO 이름 MatchedProfileFrament 로 변경
class PartnerProfileFragment: BaseFragment<PartnerProfileViewModel>(R.layout.fragment_partner_profile) {
    override val viewModel: PartnerProfileViewModel by viewModel()
    private var binding: FragmentPartnerProfileBinding? = null
    private val args: PartnerProfileFragmentArgs by navArgs()
    private val vpAdapter = MatchedProfileImageAdapter()

    private val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding?.let { b ->
                when (position) {
                    0 -> {
                        b.vIndicator1.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_selected)
                        b.vIndicator2.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_unselected)
                        b.vIndicator3.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_unselected)
                    }
                    1 -> {
                        b.vIndicator1.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_unselected)
                        b.vIndicator2.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_selected)
                        b.vIndicator3.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_unselected)
                    }
                    2 -> {
                        b.vIndicator1.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_unselected)
                        b.vIndicator2.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_unselected)
                        b.vIndicator3.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_selected)
                    }
                }
            }
            super.onPageSelected(position)
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentPartnerProfileBinding = FragmentPartnerProfileBinding.bind(view)
        binding = fragmentPartnerProfileBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initArgs()
        initViewPager2(fragmentPartnerProfileBinding)
        observeProfileImageData(fragmentPartnerProfileBinding)

    }
    private fun initArgs() {
        val userId = args.partnerUserId
        viewModel.getMatchingProfile(userId)
    }


    private fun initViewPager2(fragmentPartnerProfileBinding: FragmentPartnerProfileBinding) = with(fragmentPartnerProfileBinding) {
        vpImageContainer.adapter = vpAdapter
    }
    private fun observeProfileImageData(fragmentPartnerProfileBinding: FragmentPartnerProfileBinding) = with(fragmentPartnerProfileBinding) {
        viewModel?.imageUrls?.observe(viewLifecycleOwner) { list ->
            vpAdapter.submitList(list)
            when (list.size) {
                0 -> {
                    vpImageContainer.isGone = true
                    llIndicatorContainer.isGone = true
                }
                1 -> {
                    vpImageContainer.isGone = false
                    llIndicatorContainer.isGone = false

                    vIndicator1.isGone = false
                    vIndicator1.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_selected)
                    vIndicator2.isGone = true
                    vIndicator3.isGone = true
                }
                2 -> {
                    vpImageContainer.isGone = false
                    llIndicatorContainer.isGone = false

                    vIndicator1.isGone = false
                    vIndicator1.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_selected)
                    vIndicator2.isGone = false
                    vIndicator2.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_unselected)
                    vIndicator3.isGone = true
                }
                3 -> {
                    vpImageContainer.isGone = false
                    llIndicatorContainer.isGone = false

                    vIndicator1.isGone = false
                    vIndicator1.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_selected)
                    vIndicator2.isGone = false
                    vIndicator2.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_unselected)
                    vIndicator3.isGone = false
                    vIndicator3.setBackgroundResource(R.drawable.bg_partner_profile_image_indicator_unselected)
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        binding?.vpImageContainer?.registerOnPageChangeCallback(viewPagerCallback)
    }

    override fun onStop() {
        super.onStop()
        binding?.vpImageContainer?.unregisterOnPageChangeCallback(viewPagerCallback)

    }
}