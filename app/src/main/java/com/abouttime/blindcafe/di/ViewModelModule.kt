package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.presentation.NavHostViewModel
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.abouttime.blindcafe.presentation.main.MainViewModel
import com.abouttime.blindcafe.presentation.main.home.HomeViewModel
import com.abouttime.blindcafe.presentation.chat.gallery.GalleryViewModel
import com.abouttime.blindcafe.presentation.chat.quit.confirm.QuitViewModel
import com.abouttime.blindcafe.presentation.chat.quit.reason.QuitReasonViewModel
import com.abouttime.blindcafe.presentation.chat.report.confirm.ReportViewModel
import com.abouttime.blindcafe.presentation.chat.report.reason.ReportReasonViewModel
import com.abouttime.blindcafe.presentation.main.chat_list.ChatListViewModel
import com.abouttime.blindcafe.presentation.main.home.coffee.CoffeeOrderViewModel
import com.abouttime.blindcafe.presentation.main.my_page.MyPageViewModel
import com.abouttime.blindcafe.presentation.onboarding.agreement.AgreementViewModel
import com.abouttime.blindcafe.presentation.onboarding.login.LoginViewModel
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.essential_first.EssentialFirstViewModel
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.essential_second.EssentialSecondViewModel
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest.InterestViewModel
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail.InterestSubViewModel
import com.abouttime.blindcafe.presentation.onboarding.rule.RuleViewModel
import com.abouttime.blindcafe.presentation.onboarding.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {

    /** Splash **/
    viewModel { SplashViewModel() }

    /** Single Activity **/
    viewModel { NavHostViewModel() }


    /** On-Boarding **/
    viewModel { LoginViewModel(get()) }
    viewModel { RuleViewModel() }
    viewModel { AgreementViewModel() }
    viewModel { EssentialFirstViewModel() }
    viewModel { EssentialSecondViewModel() }
    viewModel { InterestViewModel() }
    viewModel { InterestSubViewModel(get()) }

    /** Main **/
    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { ChatListViewModel() }
    viewModel { MyPageViewModel(get()) }
    viewModel { CoffeeOrderViewModel(get()) }

    /** Chat **/
    viewModel { ChatViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { GalleryViewModel(get()) }
    viewModel { QuitViewModel() }
    viewModel { QuitReasonViewModel() }
    viewModel { ReportViewModel() }
    viewModel { ReportReasonViewModel() }

}