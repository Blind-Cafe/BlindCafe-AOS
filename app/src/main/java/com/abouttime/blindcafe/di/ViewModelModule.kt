package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.presentation.NavHostViewModel
import com.abouttime.blindcafe.presentation.main.MainViewModel
import com.abouttime.blindcafe.presentation.onboarding.agreement.AgreementViewModel
import com.abouttime.blindcafe.presentation.onboarding.login.LoginViewModel
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.essential_first.EssentialFirstViewModel
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.essential_second.EssentialSecondViewModel
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest.InterestFragment
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest.InterestViewModel
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail.InterestDetailViewModel
import com.abouttime.blindcafe.presentation.onboarding.rule.RuleViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel { NavHostViewModel() }


    viewModel { MainViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { RuleViewModel() }
    viewModel { AgreementViewModel() }
    viewModel { EssentialFirstViewModel() }
    viewModel { EssentialSecondViewModel() }
    viewModel { InterestViewModel() }
    viewModel { InterestDetailViewModel() }

}