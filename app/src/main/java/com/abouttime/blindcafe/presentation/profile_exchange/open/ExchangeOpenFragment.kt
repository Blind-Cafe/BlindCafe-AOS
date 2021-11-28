package com.abouttime.blindcafe.presentation.profile_exchange.open

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.NavigationKey
import com.abouttime.blindcafe.databinding.FragmentExchangeOpenBinding
import com.abouttime.blindcafe.databinding.FragmentProfileEditBinding
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel

class ExchangeOpenFragment: BaseFragment<ExchangeOpenViewModel>(R.layout.fragment_exchange_open) {
    override val viewModel: ExchangeOpenViewModel by viewModel()
    private var binding: FragmentExchangeOpenBinding? = null
    private val args: ExchangeOpenFragmentArgs by navArgs()
    private var hasImage = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentExchangeOpenBinding = FragmentExchangeOpenBinding.bind(view)
        binding = fragmentExchangeOpenBinding

        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        initNavArgs()

        initNicknameEditText(fragmentExchangeOpenBinding)

        observeSavedNavigationData(fragmentExchangeOpenBinding)
        observeLocationData(fragmentExchangeOpenBinding)
        observeNicknameData(fragmentExchangeOpenBinding)
        observeInterestsData(fragmentExchangeOpenBinding)
        observeProfileImageData(fragmentExchangeOpenBinding)
    }

    private fun initNavArgs() {
        val matchingId = args.matchingId
        viewModel.getProfileForOpen(matchingId)
    }





    private fun initNicknameEditText(fragmentExchangeOpenBinding: FragmentExchangeOpenBinding) = with(fragmentExchangeOpenBinding) {
        etNicknameValue.setOnFocusChangeListener { v, hasFocus ->
            etNicknameValue.isCursorVisible = hasFocus
        }
    }

    private fun observeSavedNavigationData(fragmentProfileEditBinding: FragmentExchangeOpenBinding) = with(fragmentProfileEditBinding) {
        getNavigationResult(NavigationKey.SELECT_LOCATION)?.observe(viewLifecycleOwner) { result ->
            Log.e("location -> profile edit", result)
            viewModel?.setLocation(result)

        }
    }

    private fun observeNicknameData(fragmentExchangeOpenBinding: FragmentExchangeOpenBinding) = with(fragmentExchangeOpenBinding) {
        viewModel?.nickname?.observe(viewLifecycleOwner) { nick ->

            viewModel?.updateNextButton(hasImage)
            Log.e("nickname", nick)
            if (nick.length in 1..9) {
                tvNicknameRule.setTextColor(getColorByResId(R.color.gray_300))
                vNicknameDivider.setBackgroundColor(getColorByResId(R.color.white))
            } else {
                tvNicknameRule.setTextColor(getColorByResId(R.color.alert_red))
                vNicknameDivider.setBackgroundColor(getColorByResId(R.color.alert_red))
            }

        }
    }

    private fun observeLocationData(fragmentExchangeOpenBinding: FragmentExchangeOpenBinding) = with(fragmentExchangeOpenBinding) {
        viewModel?.location?.observe(viewLifecycleOwner) { loc ->
            Log.e("profile edit", loc)
            tvLocationValue.text = loc
            etLocationValue.isGone = loc.isNotEmpty()
            viewModel?.updateNextButton(hasImage)
        }
    }

    private fun observeInterestsData(fragmentExchangeOpenBinding: FragmentExchangeOpenBinding) = with(fragmentExchangeOpenBinding) {
        val tvList = listOf(tvInterest1, tvInterest2, tvInterest3)
        viewModel?.interests?.observe(viewLifecycleOwner) { list ->
            if (list.size >= 3) {
                list.take(3).forEachIndexed { i, v ->
                    tvList[i].text = v
                }
            }


        }
    }

    private fun observeProfileImageData(fragmentExchangeOpenBinding: FragmentExchangeOpenBinding) = with(fragmentExchangeOpenBinding) {
        viewModel?.profileImage?.observe(viewLifecycleOwner) { url ->
            Glide.with(requireContext())
                .load(url)
                .into(ivProfileImage)
            hasImage = true
        }
    }

}