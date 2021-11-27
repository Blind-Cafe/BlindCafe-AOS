package com.abouttime.blindcafe.presentation.main.my_page

import android.os.Bundle
import android.view.View
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentMyPageBinding
import com.bumptech.glide.Glide
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.koin.android.viewmodel.ext.android.viewModel

class MyPageFragment : BaseFragment<MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel: MyPageViewModel by viewModel()
    private var binding: FragmentMyPageBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentMyPageBinding = FragmentMyPageBinding.bind(view)
        binding = fragmentMyPageBinding
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel


        initScrollView(fragmentMyPageBinding)

//        observeProfileImageData(fragmentMyPageBinding)
//        observeNicknameData(fragmentMyPageBinding)
//
//        observeSexData(fragmentMyPageBinding)
//        observeLocationData(fragmentMyPageBinding)
//        observeAgeData(fragmentMyPageBinding)
//        observePartnerSexData(fragmentMyPageBinding)
//
//        observeInterestsData(fragmentMyPageBinding)
//        observeDrinkData(fragmentMyPageBinding)


    }

    override fun onResume() {
        super.onResume()
        binding?.let {
            val fragmentMyPageBinding = it


            observeProfileImageData(fragmentMyPageBinding)
            observeNicknameData(fragmentMyPageBinding)

            observeSexData(fragmentMyPageBinding)
            observeLocationData(fragmentMyPageBinding)
            observeAgeData(fragmentMyPageBinding)
            observePartnerSexData(fragmentMyPageBinding)

            observeInterestsData(fragmentMyPageBinding)
            observeDrinkData(fragmentMyPageBinding)
        }

    }

    private fun initScrollView(fragmentMyPageBinding: FragmentMyPageBinding) =
        with(fragmentMyPageBinding) {
            OverScrollDecoratorHelper.setUpOverScroll(svUserInfoContainer)
        }

    private fun observeProfileImageData(fragmentMyPageBinding: FragmentMyPageBinding) =
        with(fragmentMyPageBinding) {
            viewModel?.profileImageUrl?.observe(viewLifecycleOwner) { url ->
                if (url.isNotEmpty()) {
                    Glide.with(this@MyPageFragment)
                        .load(url)
                        .circleCrop()
                        .into(ivProfileImage)
                }
            }


        }
    private fun observeNicknameData(fragmentMyPageBinding: FragmentMyPageBinding) = with(fragmentMyPageBinding) {
        viewModel?.nickname?.observe(viewLifecycleOwner) { nick ->
            tvNickname.text = nick
        }
    }

    private fun observeSexData(fragmentMyPageBinding: FragmentMyPageBinding) =
        with(fragmentMyPageBinding) {
            viewModel?.sex?.observe(viewLifecycleOwner) { sex ->
                if (sex == "F") {
                    tvSexValue.text = getStringByResId(R.string.my_page_female)
                } else {
                    tvSexValue.text = getStringByResId(R.string.my_page_male)
                }
            }
        }
    private fun observeLocationData(fragmentMyPageBinding: FragmentMyPageBinding) = with(fragmentMyPageBinding) {
        viewModel?.location?.observe(viewLifecycleOwner) { loc ->
            tvLocationValue.text = loc
        }
    }

    private fun observeAgeData(fragmentMyPageBinding: FragmentMyPageBinding) =
        with(fragmentMyPageBinding) {
            viewModel?.age?.observe(viewLifecycleOwner) { age ->
                val ageText = age + getStringByResId(R.string.my_page_age_unit)
                tvAgeValue.text = ageText
            }
        }

    private fun observeInterestsData(fragmentMyPageBinding: FragmentMyPageBinding) =
        with(fragmentMyPageBinding) {
            val interests = arrayOf(
                ivInterest1,
                ivInterest2,
                ivInterest3
            )
            viewModel?.interests?.observe(viewLifecycleOwner) { data ->
                interests[0].setImageResource(numToInterestAsset(data[0]))
                interests[1].setImageResource(numToInterestAsset(data[1]))
                interests[2].setImageResource(numToInterestAsset(data[2]))
            }
        }

    private fun observePartnerSexData(fragmentMyPageBinding: FragmentMyPageBinding) = with(fragmentMyPageBinding) {
        viewModel?.partnerSex?.observe(viewLifecycleOwner) { ps ->
            tvPartnerSexValue.text = ps
        }
    }

    private fun numToInterestAsset(num: Int): Int {
        when (num) {
            1 -> return R.drawable.bt_interest_1
            2 -> return R.drawable.bt_interest_2
            3 -> return R.drawable.bt_interest_3
            4 -> return R.drawable.bt_interest_4
            5 -> return R.drawable.bt_interest_5
            6 -> return R.drawable.bt_interest_6
            7 -> return R.drawable.bt_interest_7
            8 -> return R.drawable.bt_interest_8
            9 -> return R.drawable.bt_interest_9
            else -> return R.drawable.bt_interest_1 // TODO 삭제하는 방식 생각해보자
        }
    }

    private fun observeDrinkData(fragmentMyPageBinding: FragmentMyPageBinding) =
        with(fragmentMyPageBinding) {
            val drinks = arrayOf(
                ivCoffeeBadge1,
                ivCoffeeBadge2,
                ivCoffeeBadge3,
                ivCoffeeBadge4,
                ivCoffeeBadge5,
                ivCoffeeBadge6,
                ivCoffeeBadge7,
                ivCoffeeBadge8,
                ivCoffeeBadge9
            )
            viewModel?.badges?.observe(viewLifecycleOwner) { data ->
                for (i in data.indices) {
                    val drinkIdx = data[i] - 1
                    drinks[drinkIdx].setImageResource(numToDrinkAsset(drinkIdx))
                }
            }
        }

    private fun numToDrinkAsset(num: Int): Int {
        when (num) {
            1 -> return R.drawable.ic_drink_1
            2 -> return R.drawable.ic_drink_2
            3 -> return R.drawable.ic_drink_3
            4 -> return R.drawable.ic_drink_4
            5 -> return R.drawable.ic_drink_5
            6 -> return R.drawable.ic_drink_6
            7 -> return R.drawable.ic_drink_7
            8 -> return R.drawable.ic_drink_8
            9 -> return R.drawable.ic_drink_9
            else -> return R.drawable.ic_drink_none
        }
    }
}