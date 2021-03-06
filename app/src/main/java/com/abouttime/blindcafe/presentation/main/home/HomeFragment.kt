package com.abouttime.blindcafe.presentation.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.LogTag.HOME_TAG
import com.abouttime.blindcafe.common.constants.LogTag.RELEASE_HOME_TAG
import com.abouttime.blindcafe.common.constants.NavigationKey
import com.abouttime.blindcafe.common.constants.NavigationKey.CONFIRM_MATCHING_CANCEL
import com.abouttime.blindcafe.common.constants.Time
import com.abouttime.blindcafe.common.ext.secondToLapseForHome
import com.abouttime.blindcafe.common.ext.setMarginTop
import com.abouttime.blindcafe.databinding.FragmentHomeBinding
import com.abouttime.blindcafe.presentation.GlobalLiveData
import kotlinx.coroutines.launch
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

        Log.e(RELEASE_HOME_TAG, "onViewCreated")
        initBellImageView()

        observeHomeStatus()



        observeSavedNavigationData()
        observeGlobalHomeUpdateData()
        observeNotReadMessageCntData()



        viewModel.getHomeInfo()
    }



    //----------------------------------------------------------------------------------------

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
            b.tvStateSubTitle.setMarginTop(120)
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


    @SuppressLint("SetTextI18n")
    private fun handleOpen() {
        binding?.tvStateTitle?.text = "????????? ?????? ???"
        binding?.tvStateSubTitle?.apply {
            isGone = false
            text = "${viewModel.myNickname}?????? ???????????? ??????????????????!"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleReady() {
        binding?.tvStateTitle?.text = "????????? ?????? ???"
        binding?.tvStateSubTitle?.apply {
            isGone = false
            text = "${viewModel.myNickname}?????? ${viewModel.partnerNickname}??? ?????? ???????????? ???????????????."
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleAccept() {
        binding?.tvStateTitle?.text = "????????? ?????? ???"
        binding?.tvStateSubTitle?.apply {
            isGone = false
            text = "${viewModel.partnerNickname}?????? ????????? ???????????????"
        }
    }

    private fun handleCont() {
        binding?.tvStateTitle?.text = "????????? ????????????!"
        binding?.tvStateSubTitle?.apply {
            isGone = false
            text = "7??? ?????? ????????? ???????????? ??? ????????????!"
        }

        /** ?????? ?????? -> ??? ???????????? ?????? ??? ??? ???????????? pop ????????????. */
        viewModel.moveToExchangeCompleteFragment()
    }


    private fun handleFailedQuit() {
        /** ??? ?????? by ?????? ??? ?????? */
        viewModel.partnerNickname?.let { nick ->
            viewModel.reason?.let { r ->
                viewModel?.moveToExitFragmentByQuit(partnerNickname = nick, reason = r)
            }
        }
    }

    private fun handleFailedReported() {
        /** ??? ?????? by ????????? ??? ?????? */
        viewModel?.partnerNickname?.let { nick ->
            viewModel?.moveToExitFragmentByReport(nick)
        }
    }

    private fun handleFailedExchangeProfile() {
        /** ??? ?????? by ????????? ????????? ?????? ?????? */
        viewModel?.partnerNickname?.let { nick ->
            viewModel?.reason?.let { r ->
                viewModel?.moveToExitFragmentByDismissProfileExchange(
                    partnerNickname = nick,
                    reason = r
                )
            }
        }
    }


    //--------------------------------------------------------------------------------------------

    private fun observeSavedNavigationData() {
        getNavigationResult(CONFIRM_MATCHING_CANCEL)?.observe(viewLifecycleOwner) { result ->
            /** ?????? ???????????? ?????? ?????? ????????? ??????. **/
            if (result == NavigationKey.CONFIRM_YES) {
                viewModel.getHomeInfo()
            }

        }
    }


    private fun observeGlobalHomeUpdateData() {
        GlobalLiveData.updateHomeState.observe(viewLifecycleOwner) {
            /** ?????????????????? ???????????? ???????????? **/
            viewModel.getHomeInfo()
        }
    }

    private fun observeNotReadMessageCntData() {
        /** ????????? ????????? ??????????????? **/
        viewModel?.notReadMessageCnt.observe(viewLifecycleOwner) { cnt ->
            binding?.tvNotReadCnt?.text = getString(R.string.home_notification_message).format(cnt)
        }
    }

    private fun initBellImageView() {
        binding?.ivBell?.setOnClickListener {
            /** ivBellOn ??? 0??? ????????? ?????????. **/
            if (binding?.ivBellOn?.visibility == View.VISIBLE) {
                if (binding?.tvNotReadCnt?.visibility == View.INVISIBLE) {
                    binding?.tvNotReadCnt?.visibility = View.VISIBLE
                } else {
                    binding?.tvNotReadCnt?.visibility = View.INVISIBLE
                }
            } else {
                showToast(R.string.home_not_read_cnt_none)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.listenerRegistration?.remove()
    }
}