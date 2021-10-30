package com.abouttime.blindcafe.presentation.onboarding.rule

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.databinding.FragmentRuleBinding
import com.abouttime.blindcafe.presentation.onboarding.rule.adapter.RuleVpAdapter

class RuleFragment : Fragment(R.layout.fragment_rule) {
    private var binding: FragmentRuleBinding? = null

    private val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            when (position) {
                0 -> {
                    binding?.tvCafeRuleDescription?.setText(R.string.rule_1)
                }
                1 -> {
                    binding?.tvCafeRuleDescription?.setText(R.string.rule_2)
                }
                2 -> {
                    binding?.tvCafeRuleDescription?.setText(R.string.rule_3)
                }
                3 -> {
                    binding?.tvCafeRuleDescription?.setText(R.string.rule_4)
                }
            }
            super.onPageSelected(position)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentRuleBinding = FragmentRuleBinding.bind(view)
        binding = fragmentRuleBinding

        initViewPager(fragmentRuleBinding)

    }

    private fun initViewPager(fragmentRuleBinding: FragmentRuleBinding) =
        with(fragmentRuleBinding) {
            val adapter = RuleVpAdapter(
                listOf(
                    R.drawable.bg_rule_1,
                    R.drawable.bg_rule_2,
                    R.drawable.bg_rule_3,
                    R.drawable.bg_rule_4
                )
            )

            vpImageContainer.adapter = adapter
            diIndicator.setViewPager2(vpImageContainer)


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