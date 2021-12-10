package com.abouttime.blindcafe.presentation.edit.profile.image

import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.exifinterface.media.ExifInterface
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.util.DeviceUtil
import com.abouttime.blindcafe.databinding.FragmentProfileImageEditBinding
import com.bumptech.glide.Glide
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File


class ProfileImageEditFragment :
    BaseFragment<ProfileImageEditViewModel>(R.layout.fragment_profile_image_edit) {
    override val viewModel: ProfileImageEditViewModel by viewModel()
    private var binding: FragmentProfileImageEditBinding? = null


    private var imageCnt = 0

    private val filePath: String by lazy {
        "${requireActivity().externalCacheDir?.absolutePath}/image.jpeg"
    }

    private val galleryCallback1 =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                uploadImage(uri, 1)
            }

        }
    private val galleryCallback2 =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                uploadImage(uri, 2)
            }

        }
    private val galleryCallback3 =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                uploadImage(uri, 3)
            }
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

        initAddImageButton()

        observeImageUrlsData(fragmentProfileImageEditBinding)
        initBackButton()
        addBackPressButtonListener()
    }


    private fun initAddImageButton() {
        binding?.let {
            with(it) {
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
                val ivList = listOf(ivImage1, ivImage2, ivImage3)
                val btList = listOf(ivImageAdd1, ivImageAdd2, ivImageAdd3)
                viewModel?.imageUrls?.observe(viewLifecycleOwner) { urls ->

                    Log.e("observeImageUrlsData", urls.toString())
                    urls.forEachIndexed { i, url ->
                        if (url.isNotEmpty()) {
                            Glide.with(requireContext())
                                .load(url)
                                .into(ivList[i])

                            btList[i].setImageResource(R.drawable.bt_profile_image_delete)

                            btList[i].setOnClickListener {
                                deleteFragment(i + 1)
                            }

                            imageCnt += 1
                            Log.e("imageCnt", "$imageCnt")

                        }
                    }
                }
            }

        }


    private fun uploadImage(uri: Uri, number: Int) {


        showToast(R.string.toast_upload_image)

        //val inputStream = requireContext().contentResolver.openInputStream(uri)
        val saveFile = File(filePath)
        var bitmap: Bitmap? = null
        try {
            bitmap = if (DeviceUtil.isAndroid9Later()) {
                val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
            }

            //bitmap = rotateImage(uri, bitmap!!)
            bitmap!!.compress(Bitmap.CompressFormat.JPEG, 40, saveFile.outputStream())
        } catch (e: Exception) {
            e.printStackTrace()
            showToast(R.string.temp_error)
            return
        }


        //val file = File(path)
        val requestBody = RequestBody.create(MediaType.parse("image/*"), saveFile)
        val part = MultipartBody.Part.createFormData("image", saveFile.name, requestBody)
        val priority = RequestBody.create(MediaType.parse("text/plain"), "$number")

        viewModel?.patchProfileImage(priority, part) {
            showToast(R.string.toast_upload_image_complete)
            when (number) {
                1 -> {
                    binding?.ivImage1?.setImageBitmap(bitmap)
                    binding?.ivImageAdd1?.setImageResource(R.drawable.bt_profile_image_delete)
                    binding?.ivImageAdd1?.setOnClickListener {
                        deleteFragment(number = number)
                    }
                }
                2 -> {
                    binding?.ivImage2?.setImageBitmap(bitmap)
                    binding?.ivImageAdd2?.setImageResource(R.drawable.bt_profile_image_delete)
                    binding?.ivImageAdd2?.setOnClickListener {
                        deleteFragment(number = number)
                    }
                }
                3 -> {
                    binding?.ivImage3?.setImageBitmap(bitmap)
                    binding?.ivImageAdd3?.setImageResource(R.drawable.bt_profile_image_delete)
                    binding?.ivImageAdd3?.setOnClickListener {
                        deleteFragment(number = number)
                    }
                }
            }
            imageCnt += 1

        }


    }

    private fun rotateImage(uri: Uri, bitmap: Bitmap): Bitmap {
        val input = requireContext().contentResolver.openInputStream(uri)
        val exif = input?.let { ExifInterface(it) }
        input?.close()

        val orientation = exif?.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        val matrix = Matrix()
        Log.e("asdf", orientation.toString())
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
    }

    private fun deleteFragment(
        number: Int,
    ) {
        showToast(R.string.toast_delete_image)


        binding?.let {
            with(it) {
                viewModel?.deleteProfileImage(number) {
                    showToast(R.string.toast_delete_image_complete)
                    when (number) {
                        1 -> {
                            ivImage1.setImageResource(R.drawable.bg_profile_image)
                            ivImageAdd1.setImageResource(R.drawable.bt_profile_image_add)
                            ivImageAdd1.setOnClickListener {
                                if (DeviceUtil.hasExtrernalStoragePermission(requireContext())) {
                                    callbacks[0].launch("*/*")
                                } else {
                                    galleryPermissionCallback.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            }
                        }
                        2 -> {
                            ivImage2.setImageResource(R.drawable.bg_profile_image)
                            ivImageAdd2.setImageResource(R.drawable.bt_profile_image_add)
                            ivImageAdd2.setOnClickListener {
                                if (DeviceUtil.hasExtrernalStoragePermission(requireContext())) {
                                    callbacks[1].launch("*/*")
                                } else {
                                    galleryPermissionCallback.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            }
                        }
                        3 -> {
                            ivImage3.setImageResource(R.drawable.bg_profile_image)
                            ivImageAdd3.setImageResource(R.drawable.bt_profile_image_add)
                            ivImageAdd3.setOnClickListener {
                                if (DeviceUtil.hasExtrernalStoragePermission(requireContext())) {
                                    callbacks[2].launch("*/*")
                                } else {
                                    galleryPermissionCallback.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                }
                            }
                        }
                    }
                    imageCnt -= 1
                }
            }
        }


    }


    private fun initBackButton() {
        binding?.let { b ->
            b.ivBack.setOnClickListener {
                if (imageCnt > 0) {
                    popOneDirections()
                } else {
                    showToast(R.string.profile_image_edit_toast_alert_at_least_one)
                }
            }
        }
    }

    /** BackPressButton **/
    private fun addBackPressButtonListener() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (imageCnt > 0) {
                popOneDirections()
            } else {
                showToast(R.string.profile_image_edit_toast_alert_at_least_one)
            }
        }
    }


}