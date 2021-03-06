package com.abouttime.blindcafe.presentation.login.rule

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.BaseFragment
import com.abouttime.blindcafe.databinding.FragmentRuleBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RuleFragment : BaseFragment<RuleViewModel>(R.layout.fragment_rule) {
    private var binding: FragmentRuleBinding? = null
    override val viewModel: RuleViewModel by viewModel()

    private val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding?.tvCafeRuleDescription?.text = ""

            val phrases = getString(viewModel.rules[position])
            if (phrases.contains("[")) {
                val start = phrases.indexOf("[")
                val end = phrases.indexOf("]")
                val builder = SpannableStringBuilder(phrases.replace("[", "").replace("]", ""))
                builder.setSpan(ForegroundColorSpan(getColorByResId(R.color.main)),
                    start,
                    end - 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding?.tvCafeRuleDescription?.append(builder)
            } else {
                binding?.tvCafeRuleDescription?.text = phrases
            }
            super.onPageSelected(position)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentRuleBinding = FragmentRuleBinding.bind(view)
        binding = fragmentRuleBinding

        initViewPager(fragmentRuleBinding)
        initNextButtons(fragmentRuleBinding)
        initSkipButton(fragmentRuleBinding)

    }

    private fun initViewPager(fragmentRuleBinding: FragmentRuleBinding) =
        with(fragmentRuleBinding) {
            val adapter = RuleVpAdapter(viewModel.ruleImages)
            vpImageContainer.adapter = adapter
            diIndicator.setViewPager2(vpImageContainer)
        }


    private fun initNextButtons(fragmentRuleBinding: FragmentRuleBinding) {
        fragmentRuleBinding.tvNext.setOnClickListener {
            val currentPage = fragmentRuleBinding.vpImageContainer.currentItem
            if (currentPage >= 3) {
                moveToLoginFragment()
            } else {
                val nextPage = currentPage + 1
                fragmentRuleBinding.vpImageContainer.setCurrentItem(nextPage, true)
            }
        }
    }


    private fun initSkipButton(fragmentRuleBinding: FragmentRuleBinding) {
        fragmentRuleBinding.tvSkip.setOnClickListener {
            moveToLoginFragment()
        }
    }

    private fun moveToLoginFragment() {
        moveToDirections(RuleFragmentDirections.actionRuleFragmentToLoginFragment())
    }


    override fun onStart() {
        super.onStart()
        binding?.vpImageContainer?.registerOnPageChangeCallback(viewPagerCallback)
    }

    override fun onStop() {
        super.onStop()
        binding?.vpImageContainer?.unregisterOnPageChangeCallback(viewPagerCallback)

    }
}