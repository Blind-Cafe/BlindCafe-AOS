package com.abouttime.blindcafe.presentation.main.my_page.edit.profile.image

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.DeviceUtil
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

        initAddImageButton(fragmentProfileImageEditBinding)

        observeImageUrlsData(fragmentProfileImageEditBinding)
    }



    private fun initAddImageButton(fragmentProfileImageEditBinding: FragmentProfileImageEditBinding) = with(fragmentProfileImageEditBinding) {
        val btList = listOf(ivImageAdd1, ivImageAdd2, ivImageAdd3)

        btList.forEachIndexed { i, iv ->
            iv.setOnClickListener {
                val galleryCallback =
                    registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                        iv.setImageURI(uri)
                    }

                val galleryPermissionCallback =
                    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                        if (isGranted) {
                            galleryCallback.launch("image/*")
                        } else {
                            showToast(R.string.chat_toast_permission)
                        }
                    }

                if (DeviceUtil.hasExtrernalStoragePermission(requireContext())) {
                    galleryCallback.launch("image/*")
                } else {
                    galleryPermissionCallback.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }





    private fun observeImageUrlsData(fragmentProfileImageEditBinding: FragmentProfileImageEditBinding) = with(fragmentProfileImageEditBinding) {
        viewModel?.imageUrls?.observe(viewLifecycleOwner) { urls ->
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