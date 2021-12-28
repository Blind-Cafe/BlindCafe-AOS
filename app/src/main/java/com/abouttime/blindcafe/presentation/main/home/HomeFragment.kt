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
        binding?.tvStateTitle?.text = "프로필 교환 중"
        binding?.tvStateSubTitle?.apply {
            isGone = false
            text = "${viewModel.myNickname}님의 프로필을 공개해주세요!"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleReady() {
        binding?.tvStateTitle?.text = "프로필 교환 중"
        binding?.tvStateSubTitle?.apply {
            isGone = false
            text = "${viewModel.myNickname}님과 ${viewModel.partnerNickname}님 모두 수락해야 매칭됩니다."
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleAccept() {
        binding?.tvStateTitle?.text = "프로필 교환 중"
        binding?.tvStateSubTitle?.apply {
            isGone = false
            text = "${viewModel.partnerNickname}님의 수락을 기다립니다"
        }
    }

    private fun handleCont() {
        binding?.tvStateTitle?.text = "프로필 교환성공!"
        binding?.tvStateSubTitle?.apply {
            isGone = false
            text = "7일 동안 추가로 대화하실 수 있습니다!"
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


    //--------------------------------------------------------------------------------------------

    private fun observeSavedNavigationData() {
        getNavigationResult(CONFIRM_MATCHING_CANCEL)?.observe(viewLifecycleOwner) { result ->
            /** 매칭 취소하고 오면 다시 최신화 한다. **/
            if (result == NavigationKey.CONFIRM_YES) {
                viewModel.getHomeInfo()
            }

        }
    }


    private fun observeGlobalHomeUpdateData() {
        GlobalLiveData.updateHomeState.observe(viewLifecycleOwner) {
            /** 푸시메시지가 전송되면 업데이트 **/
            viewModel.getHomeInfo()
        }
    }

    private fun observeNotReadMessageCntData() {
        /** 몇건의 안읽은 메시지인지 **/
        viewModel?.notReadMessageCnt.observe(viewLifecycleOwner) { cnt ->
            binding?.tvNotReadCnt?.text = getString(R.string.home_notification_message).format(cnt)
        }
    }

    private fun initBellImageView() {
        binding?.ivBell?.setOnClickListener {
            /** ivBellOn 은 0이 아니면 보인다. **/
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