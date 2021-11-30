package com.abouttime.blindcafe.presentation.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.LogTag.HOME_TAG
import com.abouttime.blindcafe.common.constants.NavigationKey.CONFIRM_MATCHING_CANCEL
import com.abouttime.blindcafe.common.constants.Time
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
                4 -> handleOpen()
                5 -> handleReady()
                6 -> handleAccept()
                7 -> handleCont()
                8 -> handleFailedQuit()
                9 -> handleFailedReported()
                10 -> handleFailedExchangeProfile()
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
            b.tvStateSubTitle.setMarginTop(64)
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
                    ((((System.currentTimeMillis() / 1000) - time.toLong()) / (Time.HOUR_72 * 60 * 60).toFloat()) * 100)
                b.cpbLeftTime.progress = progress
            }
            val time = viewModel?.startTime?.toLong()?.secondToLapseForHome()
            b.tvTime.text = time
            Log.e(HOME_TAG, time.toString())
        }
    }


    private fun handleOpen() {
        binding?.tvStateSubTitle?.apply {
            isGone = false
            text = "프로필 교환 중"
        }
    }

    private fun handleReady() {
        binding?.tvStateSubTitle?.apply {
            isGone = false
            text = "프로필 교환 중"
        }
    }

    private fun handleAccept() {
        binding?.tvStateSubTitle?.apply {
            isGone = false
            text = "프로필 교환 중"
        }
    }

    private fun handleCont() {
        binding?.tvStateSubTitle?.apply {
            isGone = false
            text = "프로필 교환 중"
        }

        /** 매칭 성공 -> 내 테이블로 이동 할 때 성공화면 pop 해야한다. */
        viewModel.moveToExchangeCompleteFragment()
    }


    private fun handleFailedQuit() {
        /** 방 폭파 by 상대 방 나감 */
        viewModel.partnerNickname?.let { nick ->
            viewModel.reason?.let { r ->
                viewModel?.moveToExitFragmentByQuit(partnerNickname = nick, reason = r)
            }
        }
    }

    private fun handleFailedReported() {
        /** 방 폭파 by 상대가 나 신고 */
        viewModel?.partnerNickname?.let { nick ->
            viewModel?.moveToExitFragmentByReport(nick)
        }

    }

    private fun handleFailedExchangeProfile() {
        /** 방 폭파 by 상대가 프로필 교환 거절 */
        viewModel?.partnerNickname?.let { nick ->
            viewModel?.reason?.let { r ->
                viewModel?.moveToExitFragmentByDismissProfileExchange(
                    partnerNickname = nick,
                    reason = r
                )
            }
        }
    }

    private fun observeSavedNavigationData() {
        getNavigationResult(CONFIRM_MATCHING_CANCEL)?.observe(viewLifecycleOwner) { result ->
            Log.e("navigation", "observeSavedNavigationData Home")
            viewModel.getHomeInfo()
        }
    }
}