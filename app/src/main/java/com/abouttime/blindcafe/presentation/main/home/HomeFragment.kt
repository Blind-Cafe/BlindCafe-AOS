package com.abouttime.blindcafe.presentation.main.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.marginTop
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.ext.setMarginTop
import com.abouttime.blindcafe.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment: BaseFragment<HomeViewModel>(R.layout.fragment_home) {
    private var binding: FragmentHomeBinding? = null
    override val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this

        observeHomeStatus()
    }


    override fun onResume() {
        super.onResume()
        viewModel.getHomeInfo()
    }

    private fun observeHomeStatus() {
        viewModel.homeStatusCode.observe(viewLifecycleOwner) { statusCode ->
            when(statusCode) {
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
        }
    }

    private fun handleStatusMatching() {
        binding?.let { b ->
            b.tvStateTitle.apply {
                isGone = false
                text = getString(R.string.home_title_matching)
            }
            b.tvStateSubTitle.text = getString(R.string.home_subtitle_matching)
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

}