package com.abouttime.blindcafe.presentation.profile_exchange.open

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isGone
import androidx.navigation.fragment.navArgs
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.common.constants.NavigationKey.SELECT_LOCATION
import com.abouttime.blindcafe.databinding.FragmentExchangeOpenBinding
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
        observePartnerNicknameData()
    }

    private fun initNavArgs() {
        val matchingId = args.matchingId
        viewModel.matchingId = args.matchingId
        viewModel.getProfileForOpen(matchingId)
    }





    private fun initNicknameEditText(fragmentExchangeOpenBinding: FragmentExchangeOpenBinding) = with(fragmentExchangeOpenBinding) {
        etNicknameValue.setOnFocusChangeListener { v, hasFocus ->
            etNicknameValue.isCursorVisible = hasFocus
        }
    }

    private fun observeSavedNavigationData(fragmentProfileEditBinding: FragmentExchangeOpenBinding) = with(fragmentProfileEditBinding) {
        getNavigationResult(SELECT_LOCATION)?.observe(viewLifecycleOwner) { result ->
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

            url?.let {
                Glide.with(requireContext())
                    .load(url)
                    .circleCrop()
                    .into(ivProfileImage)
                hasImage = true
            } ?: kotlin.run {
                ivProfileImage.setImageResource(R.drawable.ic_profile_image_none)
                hasImage = false
            }

        }
    }


    private fun observePartnerNicknameData() {
        viewModel?.partnerNickname.observe(viewLifecycleOwner) { pName ->
            binding?.let { b ->
                b.tvSubtitle1.text  = getStringByResId(R.string.profile_exchange_subtitle_1).format(pName)
                b.tvSubtitle2.text = getStringByResId(R.string.profile_exchange_subtitle_2).format(pName)
                b.tvSubtitle3.text = getStringByResId(R.string.exchange_open_alert).format(pName)
            }

        }

    }

}