package com.abouttime.blindcafe.presentation.main.my_page.edit.profile.image

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.util.DeviceUtil
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentProfileImageEditBinding
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileImageEditFragment: BaseFragment<ProfileImageEditViewModel>(R.layout.fragment_profile_image_edit) {
    override val viewModel: ProfileImageEditViewModel by viewModel()
    private var binding: FragmentProfileImageEditBinding? = null


    val galleryCallback1 =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding?.ivImage1?.setImageURI(uri)
        }
    val galleryCallback2 =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding?.ivImage2?.setImageURI(uri)
        }
    val galleryCallback3 =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding?.ivImage3?.setImageURI(uri)
        }
    
    val callbacks = listOf(
        galleryCallback1,
        galleryCallback2,
        galleryCallback3
    )
    
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentProfileImageEditBinding = FragmentProfileImageEditBinding.bind(view)
        binding = fragmentProfileImageEditBinding

        initAddImageButton(fragmentProfileImageEditBinding)

        observeImageUrlsData(fragmentProfileImageEditBinding)
    }



    private fun initAddImageButton(fragmentProfileImageEditBinding: FragmentProfileImageEditBinding) = with(fragmentProfileImageEditBinding) {
        val btList = listOf(ivImageAdd1, ivImageAdd2, ivImageAdd3)
        val ivList = listOf(ivImage1, ivImage2, ivImage3)
        
        btList.forEachIndexed { i, iv ->
            iv.setOnClickListener {
                if (DeviceUtil.hasExtrernalStoragePermission(requireContext())) {
                    callbacks[i].launch("image/*")
                } else {
                    galleryPermissionCallback.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }
   
    

    val galleryPermissionCallback =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                showToast(R.string.chat_toast_permission)
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