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

    private fun handleStatusFailedLeaveRoom() {
        binding?.let { b ->
            b.tvStateTitle.apply {
                isGone = false
                text = getString(R.string.home_title_matching)
            }
            b.tvStateSubTitle.text = getString(R.string.home_subtitle_matching)
        }
    }

    private fun handleStatusFailedReport() {
        binding?.let { b ->
            b.tvStateTitle.apply {
                isGone = false
                text = getString(R.string.home_title_matching)
            }
            b.tvStateSubTitle.text = getString(R.string.home_subtitle_matching)
        }
    }

    private fun handleWontExchange() {
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
}