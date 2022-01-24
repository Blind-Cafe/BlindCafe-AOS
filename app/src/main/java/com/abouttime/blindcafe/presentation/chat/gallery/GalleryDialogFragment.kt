package com.abouttime.blindcafe.presentation.chat.gallery

import android.annotation.SuppressLint
import android.content.ContentUris
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.abouttime.blindcafe.common.constants.LogTag.BOTTOM_SHEET
import com.abouttime.blindcafe.common.util.RvGridDecoration
import com.abouttime.blindcafe.data.local.media_store.Image
import com.abouttime.blindcafe.data.local.media_store.MediaStoreAdapter
import com.abouttime.blindcafe.databinding.DialogFragmentGalleryBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class GalleryDialogFragment(
    private val userId: String,
    private val matchingId: Int
): BottomSheetDialogFragment() {
    private var binding: DialogFragmentGalleryBinding? = null
    private lateinit var rvAdapter: GalleryRvAdapter
    private val viewModel: GalleryViewModel by viewModel()
    private var cursor: Cursor? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val dialogFragmentGalleryBinding = DialogFragmentGalleryBinding.inflate(inflater, container, false)
        binding = dialogFragmentGalleryBinding


        return dialogFragmentGalleryBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initArgs()
        //initCursor()
        initImageRecyclerView()
        initBottomSheetDialog()



        //loadNextImages()


        observePagedImageList()
        initSendButton()
        //observeImageItems()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initArgs() {
        viewModel.userId = userId
        viewModel.matchingId = matchingId
    }


    private fun initImageRecyclerView() {
        binding?.rvPictureContainer?.let {
            rvAdapter = GalleryRvAdapter(viewModel)
            it.adapter = rvAdapter
            it.layoutManager = GridLayoutManager(context, 3)
            it.addItemDecoration(RvGridDecoration(3, 6, false))
        }
    }



    @SuppressLint("Range")
    private fun getMediaList(loadSize: Int): ArrayList<Image?> {
        val imageList = ArrayList<Image?>()
        cursor?.let { c ->
            repeat(loadSize) {
                if (c.moveToNext()) {
                    val id = c.getLong(c.getColumnIndex(MediaStore.Images.ImageColumns._ID)) ?: 0L
                    val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    imageList.add(Image(uri = uri))
                }
            }
        }
        return imageList
    }

    private fun initSendButton() {
        binding?.tvSend?.setOnClickListener {
            viewModel.onClickSendButton()
            dismiss()
        }
    }

    private fun observePagedImageList() {
        viewModel.images.observe(viewLifecycleOwner) { pagedList ->
            rvAdapter.submitList(pagedList)
        }
    }





    private fun initBottomSheetDialog() {

        val bottomSheet = binding?.nsGalleryContainer
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

//        binding?.rvPictureContainer?.setOnTouchListener { view, motionEvent ->
//            view.parent.requestDisallowInterceptTouchEvent(true)
//            view.onTouchEvent(motionEvent)
//            true
//        }

        behavior.isDraggable = false

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Log.e(BOTTOM_SHEET, newState.toString())
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding?.tvSend?.let { setBias(it,  1.0f) }
                } else if (newState == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                    binding?.tvSend?.let { setBias(it,  0.6f) }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.e(BOTTOM_SHEET, slideOffset.toString())
                //behavior.state = BottomSheetBehavior.STATE_EXPANDED
                binding?.tvSend?.let {
                    if (slideOffset <= 0) {
                        val value = bottomSheet.y + behavior.peekHeight - it.height
                        it.animate()?.y(value)?.setDuration(0)?.start()
                    } else {
                        val value = bottomSheet.height - it.height
                        it.animate()?.y(value.toFloat())?.setDuration(0)?.start()
                    }
                }


            }
        })

    }


    private fun initCursor() {
        cursor = MediaStoreAdapter().getCursor(requireActivity())
        cursor?.moveToFirst()
    }

    private fun loadNextImages() {
        //rvAdapter.submitImageList(getMediaList(20))
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun setBias(v: View, bias: Float) {
        if (v.layoutParams is ConstraintLayout.LayoutParams) {
            val param = v.layoutParams as ConstraintLayout.LayoutParams
            param.verticalBias = bias
        }
    }

}