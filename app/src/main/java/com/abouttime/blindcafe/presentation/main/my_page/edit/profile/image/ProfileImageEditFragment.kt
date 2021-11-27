package com.abouttime.blindcafe.presentation.main.my_page.edit.profile.image

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.util.DeviceUtil
import com.abouttime.blindcafe.databinding.FragmentProfileImageEditBinding
import com.bumptech.glide.Glide
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.*
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore


class ProfileImageEditFragment :
    BaseFragment<ProfileImageEditViewModel>(R.layout.fragment_profile_image_edit) {
    override val viewModel: ProfileImageEditViewModel by viewModel()
    private var binding: FragmentProfileImageEditBinding? = null


    private val filePath: String by lazy {
        "${requireActivity().externalCacheDir?.absolutePath}/image.jpeg"
    }

    private val galleryCallback1 =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding?.ivImage1?.setImageURI(uri)




            uploadImage(uri, 1)
        }
    private val galleryCallback2 =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding?.ivImage2?.setImageURI(uri)
            uploadImage(uri, 2)
        }
    private val galleryCallback3 =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding?.ivImage3?.setImageURI(uri)
            uploadImage(uri, 3)
        }

    private val callbacks = listOf(
        galleryCallback1,
        galleryCallback2,
        galleryCallback3
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentProfileImageEditBinding = FragmentProfileImageEditBinding.bind(view)
        binding = fragmentProfileImageEditBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initAddImageButton(fragmentProfileImageEditBinding)

        observeImageUrlsData(fragmentProfileImageEditBinding)
    }


    private fun initAddImageButton(fragmentProfileImageEditBinding: FragmentProfileImageEditBinding) =
        with(fragmentProfileImageEditBinding) {
            val btList = listOf(ivImageAdd1, ivImageAdd2, ivImageAdd3)

            btList.forEachIndexed { i, iv ->
                iv.setOnClickListener {

                    if (DeviceUtil.hasExtrernalStoragePermission(requireContext())) {
                        callbacks[i].launch("*/*")
                    } else {
                        galleryPermissionCallback.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
            }
        }

    private val galleryPermissionCallback =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                showToast(R.string.chat_toast_permission)
            }
        }

    private fun observeImageUrlsData(fragmentProfileImageEditBinding: FragmentProfileImageEditBinding) =
        with(fragmentProfileImageEditBinding) {
            viewModel?.let {
                Log.d("observeImageUrlsData", "viewModel is not null")
                viewModel?.imageUrls?.observe(viewLifecycleOwner) { urls ->
                    val ivList = listOf(ivImage1, ivImage2, ivImage3)
                    Log.e("observeImageUrlsData", urls.toString())
                    urls.forEachIndexed { i, url ->
                        if (url.isNotEmpty()) {
                            Glide.with(requireContext())
                                .load(url)
                                .into(ivList[i])
                        }
                    }

                }
            }

        }


    private fun uploadImage(uri: Uri, priority: Int) {

        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val saveFile = File(filePath)
        val bitmap : Bitmap? =null
        try {
            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri).compress(Bitmap.CompressFormat.JPEG, 40,  saveFile.outputStream()) //TODO try catch
        } catch (e: Exception) {
            e.printStackTrace()
            showToast(R.string.temp_error)
            return
        }
        Log.e("uploadImage", uri.toString())
        Log.e("uploadImage", uri.path.toString())


        //val file = File(path)
        val requestBody = RequestBody.create(MediaType.parse("image/*"), saveFile)
        val part = MultipartBody.Part.createFormData("image", saveFile.name, requestBody)
        val priority = RequestBody.create(MediaType.parse("text/plain"), "$priority")

        viewModel?.patchProfileImage(priority, part)


    }

    /** InputStream 클래스 확장 */
    fun InputStream.toFile(filePath: String) {
        File(filePath).outputStream().use { fileOutput ->
            this.copyTo(fileOutput)
        }
    }



}