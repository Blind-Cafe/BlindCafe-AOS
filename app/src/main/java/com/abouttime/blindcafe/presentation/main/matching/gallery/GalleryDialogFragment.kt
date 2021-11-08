package com.abouttime.blindcafe.presentation.main.matching.gallery

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.GridLayoutManager
import com.abouttime.blindcafe.common.RvGridDecoration
import com.abouttime.blindcafe.common.constants.LogTag.BOTTOM_SHEET
import com.abouttime.blindcafe.databinding.DialogFragmentGalleryBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.newSingleThreadContext

class GalleryDialogFragment: BottomSheetDialogFragment() {
    private var binding: DialogFragmentGalleryBinding? = null
    private val rvAdapter: GalleryRvAdapter = GalleryRvAdapter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return super.onCreateDialog(savedInstanceState)
    }

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

        initImageRecyclerView()
        initBottomSheetDialog()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initImageRecyclerView() {
        binding?.rvPictureContainer?.let {
            it.adapter = rvAdapter
            it.layoutManager = GridLayoutManager(context, 3)
            it.addItemDecoration(RvGridDecoration(3, 4, false))
        }
    }


    private fun initBottomSheetDialog() {

        val bottomSheet = binding?.clGalleryContainer
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
        bottomSheet.addOnLayoutChangeListener(View.OnLayoutChangeListener { p0, p1, p2, p3, p4, p5, p6, p7, p8 ->

        })


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
                binding?.tvSend?.let {
                    val density = resources.displayMetrics.density

                    val currentMargin = it.marginBottom
                    val value = (slideOffset.toInt() / density).toInt()
                    val margin = currentMargin + value

                    setMargins(it, 16, margin, 16, margin)
                }

            }
        })

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


    fun setMargins(v: View, l: Int, t: Int, r: Int, b: Int) {
        if (v.layoutParams is MarginLayoutParams) {
            val p = v.layoutParams as MarginLayoutParams
            p.setMargins(l, t, r, b)
            v.requestLayout()
        }
    }



}