package com.abouttime.blindcafe.presentation.onboarding.rule

import com.abouttime.blindcafe.R
import com.abouttime.blindcafe.common.base.view_model.BaseOnBoardingViewModel

class RuleViewModel: BaseOnBoardingViewModel() {
    val rules = listOf(
        R.string.rule_1,
        R.string.rule_2,
        R.string.rule_3,
        R.string.rule_4
    )

    val ruleImages = listOf(
        R.drawable.bg_rule_1,
        R.drawable.bg_rule_2,
        R.drawable.bg_rule_3,
        R.drawable.bg_rule_4
    )
}