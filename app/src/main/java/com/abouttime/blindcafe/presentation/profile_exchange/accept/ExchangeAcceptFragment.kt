package com.abouttime.blindcafe.presentation.profile_exchange.accept

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentExchangeAcceptBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ExchangeAcceptFragment: BaseFragment<ExchangeAcceptViewModel>(R.layout.fragment_exchange_accept) {
    override val viewModel: ExchangeAcceptViewModel by viewModel()
    private var binding: FragmentExchangeAcceptBinding? = null
    private val args: ExchangeAcceptFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentExchangeAcceptBinding = FragmentExchangeAcceptBinding.bind(view)
        binding = fragmentExchangeAcceptBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initArgs()
        observeProfileData(fragmentExchangeAcceptBinding)
    }

    private fun initArgs() {
        viewModel.matchingId = args.matchingId
        viewModel.getPartnerProfile(args.matchingId)
    }

    @SuppressLint("SetTextI18n")
    private fun observeProfileData(fragmentExchangeAcceptBinding: FragmentExchangeAcceptBinding) = with(fragmentExchangeAcceptBinding) {
        viewModel?.partnerProfile?.observe(viewLifecycleOwner) { profile ->
            profile.nickname?.let { nick ->
                tvSubtitle1.text  = getString(R.string.profile_exchange_subtitle_1).format(nick)
                tvSubtitle2.text  = getString(R.string.profile_exchange_subtitle_2).format(nick)
                tvSubtitle3.text  = getString(R.string.profile_exchange_subtitle_3).format(nick)
                tvProfileNickname.text = nick
            }
            profile.location?.let { loc ->
                tvLocation.text = loc
            }
            val tvList = listOf(tvInterest1, tvInterest2, tvInterest3)
            profile.interests?.let { list ->
                list.forEachIndexed { i, v ->
                    tvList[i].text = v
                }
            }
            profile.sex?.let {  sex ->
                tvSexValue.text = sex
            }
            profile.age?.let { age ->
                tvAgeValue.text = "${age}세"
            }
        }
    }
}