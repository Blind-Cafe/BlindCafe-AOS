package com.abouttime.blindcafe.presentation.main.my_page

import android.os.Bundle
import android.util.Log
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

        observeProfileImageData(fragmentMyPageBinding)

        observeLocationData(fragmentMyPageBinding)

        observeInterestsData(fragmentMyPageBinding)
        observeDrinkData(fragmentMyPageBinding)


    }


    private fun initScrollView(fragmentMyPageBinding: FragmentMyPageBinding) =
        with(fragmentMyPageBinding) {
            OverScrollDecoratorHelper.setUpOverScroll(svUserInfoContainer)
        }

    private fun observeLocationData(fragmentMyPageBinding: FragmentMyPageBinding) = with(fragmentMyPageBinding) {
        viewModel?.location?.observe(viewLifecycleOwner) { loc ->
            loc?.let {
                tvLocationValue.text = it
                tvLocationValue.setTextColor(getColorByResId(R.color.white_2))
            } ?: kotlin.run {
                tvLocationValue.text = "지역을 설정해주세요"
                tvLocationValue.setTextColor(getColorByResId(R.color.gray_300))
            }

        }
    }

    private fun observeProfileImageData(fragmentMyPageBinding: FragmentMyPageBinding) =
        with(fragmentMyPageBinding) {
            viewModel?.profileImageUrl?.observe(viewLifecycleOwner) { url ->
                Log.e("myPage", url)
                if (url.isNotEmpty()) {
                    Glide.with(this@MyPageFragment)
                        .load(url)
                        .circleCrop()
                        .into(ivProfileImage)
                }
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
                Log.e("myPage", data.toString())
                interests[0].setImageResource(numToInterestAsset(data[0]))
                interests[1].setImageResource(numToInterestAsset(data[1]))
                interests[2].setImageResource(numToInterestAsset(data[2]))
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
                Log.e("myPage", data.toString())
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