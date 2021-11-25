package com.abouttime.blindcafe.presentation.main.my_page.edit.profile.image

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.base.BaseViewModel
import com.abouttime.blindcafe.databinding.FragmentProfileImageEditBinding
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileImageEditFragment: BaseFragment<ProfileImageEditViewModel>(R.layout.fragment_profile_image_edit) {
    override val viewModel: ProfileImageEditViewModel by viewModel()
    private var binding: FragmentProfileImageEditBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentProfileImageEditBinding = FragmentProfileImageEditBinding.bind(view)
        binding = fragmentProfileImageEditBinding



        observeImageUrlsData(fragmentProfileImageEditBinding)
    }

    private fun observeImageUrlsData(fragmentProfileImageEditBinding: FragmentProfileImageEditBinding) = with(fragmentProfileImageEditBinding) {
        viewModel.imageUrls.observe(viewLifecycleOwner) { urls ->
            val ivList = listOf(ivImage1, ivImage2, ivImage3)
            urls.forEachIndexed { i, url ->
                if (!url.isNullOrEmpty()) {
                    Glide.with(requireContext())
                        .load(url)
                        .into(ivList[i])
                }
            }

        }
    }
}