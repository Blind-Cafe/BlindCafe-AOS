package com.abouttime.blindcafe.presentation.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.common.constants.LogTag.HOME_TAG
import com.abouttime.blindcafe.common.constants.NavigationKey.CONFIRM_MATCHING_CANCEL
import com.abouttime.blindcafe.common.ext.secondToLapseForHome
import com.abouttime.blindcafe.common.ext.setMarginTop
import com.abouttime.blindcafe.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel>(R.layout.fragment_home) {
    private var binding: FragmentHomeBinding? = null
    override val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this



        observeHomeStatus()
        observeSavedNavigationData()
    }


    private fun observeHomeStatus() {
        viewModel.homeStatusCode.observe(viewLifecycleOwner) { statusCode ->
            when (statusCode) {
                0 -> handleStatusNone()
                1 -> handleStatusWait()
                2 -> handleStatusFound()
                3 -> handleStatusMatching()
                4 -> handleStatusFailedLeaveRoom()
                5 -> handleStatusFailedReport()
                6 -> handleWontExchange()
            }
        }
    }

    private fun handleStatusNone() {
        binding?.let { b ->
            b.tvStateTitle.apply {
                isGone = false
                text = getString(R.string.home_title_none)
            }
            b.tvStateSubTitle.text = getString(R.string.home_subtitle_none)

        }

    }

    private fun handleStatusWait() {
        binding?.let { b ->
            b.tvStateTitle.isGone = true
            b.tvStateSubTitle.text = getString(R.string.home_subtitle_wait)
            b.tvStateSubTitle.setMarginTop(48)
        }


    }

    private fun handleStatusFound() {
        binding?.let { b ->
            b.tvStateTitle.apply {
                isGone = false
                text = getString(R.string.home_title_found)
            }
            b.tvStateSubTitle.text = getString(R.string.home_subtitle_found)
            b.tvTime.text = "00:00"
        }
    }

    private fun handleStatusMatching() {
        binding?.let { b ->
            b.tvStateTitle.apply {
                isGone = false
                text = getString(R.string.home_title_matching)
            }
            b.tvStateSubTitle.text = getString(R.string.home_subtitle_matching)

            viewModel?.startTime?.let { time ->
                val progress =
                    ((((System.currentTimeMillis() / 1000) - time.toLong()) / (72 * 60 * 60).toFloat()) * 100)
                Log.e(HOME_TAG, progress.toString())
                b.cpbLeftTime.progress = progress
            }
            val time = viewModel?.startTime?.toLong()?.secondToLapseForHome()
            b.tvTime.text = time
            Log.e(HOME_TAG, time.toString())

        }
    }

    private fun handleStatusFailedLeaveRoom() { // TODO 어떻게 처리할지 기획 질문할 것!
        binding?.let { b ->
            b.tvStateTitle.apply {
                isGone = false
                text = getString(R.string.home_title_matching)
            }
            b.tvStateSubTitle.text = getString(R.string.home_subtitle_matching)
        }
    }

    private fun handleStatusFailedReport() { // TODO 어떻게 처리할지 기획 질문할 것!
        binding?.let { b ->
            b.tvStateTitle.apply {
                isGone = false
                text = getString(R.string.home_title_matching)
            }
            b.tvStateSubTitle.text = getString(R.string.home_subtitle_matching)
        }
    }

    private fun handleWontExchange() { // TODO 어떻게 처리할지 기획 질문할 것!
        binding?.let { b ->
            b.tvStateTitle.apply {
                isGone = false
                text = getString(R.string.home_title_matching)
            }
            b.tvStateSubTitle.text = getString(R.string.home_subtitle_matching)
        }
    }


    private fun observeSavedNavigationData() {
        getNavigationResult(CONFIRM_MATCHING_CANCEL)?.observe(viewLifecycleOwner) { result ->
            Log.e("navigation", "observeSavedNavigationData Home")
            viewModel.getHomeInfo()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(LogTag.LIFECYCLE_TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d(LogTag.LIFECYCLE_TAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(LogTag.LIFECYCLE_TAG, "onViewStateRestored")

    }

    override fun onStart() {
        super.onStart()
        Log.d(LogTag.LIFECYCLE_TAG, "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.d(LogTag.LIFECYCLE_TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LogTag.LIFECYCLE_TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LogTag.LIFECYCLE_TAG, "onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LogTag.LIFECYCLE_TAG, "onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(LogTag.LIFECYCLE_TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LogTag.LIFECYCLE_TAG, "onDestroy")
    }


}