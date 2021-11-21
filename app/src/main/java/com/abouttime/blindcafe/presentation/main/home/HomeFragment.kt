package com.abouttime.blindcafe.presentation.main.home

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
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

    }
    private fun handleStatusWait() {

    }
    private fun handleStatusMatching() {

    }
    private fun handleStatusFound() {

    }
    private fun handleStatusFailedLeaveRoom() {

    }
    private fun handleStatusFailedReport() {

    }
    private fun handleWontExchange() {

    }

}