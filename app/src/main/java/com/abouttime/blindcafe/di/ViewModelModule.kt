package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.presentation.NavHostViewModel
import com.abouttime.blindcafe.presentation.chat.ChatViewModel
import com.abouttime.blindcafe.presentation.main.MainViewModel
import com.abouttime.blindcafe.presentation.main.home.HomeViewModel
import com.abouttime.blindcafe.presentation.chat.gallery.GalleryViewModel
import com.abouttime.blindcafe.presentation.chat.quit.confirm.QuitViewModel
import com.abouttime.blindcafe.presentation.chat.quit.reason.QuitReasonViewModel
import com.abouttime.blindcafe.presentation.chat.report.confirm.ReportViewModel
import com.abouttime.blindcafe.presentation.chat.report.ReportReasonViewModel
import com.abouttime.blindcafe.presentation.common.confirm.ConfirmViewModel
import com.abouttime.blindcafe.presentation.main.chat_list.ChatListViewModel
import com.abouttime.blindcafe.presentation.main.dormancy.DormancyViewModel
import com.abouttime.blindcafe.presentation.main.home.coffee.CoffeeOrderViewModel
import com.abouttime.blindcafe.presentation.main.home.exit.ExitViewModel
import com.abouttime.blindcafe.presentation.main.my_page.MyPageViewModel
import com.abouttime.blindcafe.presentation.main.my_page.edit.ProfileEditViewModel
import com.abouttime.blindcafe.presentation.main.my_page.setting.SettingViewModel
import com.abouttime.blindcafe.presentation.main.my_page.setting.account_delete.AccountDeleteViewModel
import com.abouttime.blindcafe.presentation.main.my_page.setting.report_list.ReportListViewModel
import com.abouttime.blindcafe.presentation.onboarding.agreement.AgreementViewModel
import com.abouttime.blindcafe.presentation.onboarding.login.LoginViewModel
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.essential_first.EssentialFirstViewModel
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.essential_second.EssentialSecondViewModel
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest.InterestViewModel
import com.abouttime.blindcafe.presentation.onboarding.profile_setting.interest_detail.InterestSubViewModel
import com.abouttime.blindcafe.presentation.onboarding.rule.RuleViewModel
import com.abouttime.blindcafe.presentation.onboarding.splash.SplashViewModel
import com.abouttime.blindcafe.presentation.profile_exchange.ProfileExchangeViewModel
import com.abouttime.blindcafe.presentation.profile_exchange.complete.ProfileExchangeCompleteViewModel
import com.abouttime.blindcafe.presentation.profile_exchange.dismiss.ProfileDismissViewModel
import com.abouttime.blindcafe.presentation.profile_exchange.location.LocationViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {

    /** Splash **/
    viewModel { SplashViewModel() }


    /** Common **/
    viewModel { ConfirmViewModel()}


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
    viewModel { CoffeeOrderViewModel(get()) }
    viewModel { ExitViewModel() }

    viewModel { DormancyViewModel() }


    viewModel { ChatListViewModel() }

    viewModel { MyPageViewModel(get()) }
    viewModel { SettingViewModel() }
    viewModel { ProfileEditViewModel() }
    viewModel { AccountDeleteViewModel() }
    viewModel { ReportListViewModel() }



    /** Chat **/
    viewModel { ChatViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { GalleryViewModel(get()) }
    viewModel { QuitViewModel() }
    viewModel { QuitReasonViewModel() }
    viewModel { ReportViewModel() }
    viewModel { ReportReasonViewModel() }

    /** Profile-Exchange **/
    viewModel { ProfileExchangeViewModel() }
    viewModel { ProfileExchangeCompleteViewModel() }
    viewModel { ProfileDismissViewModel() }
    viewModel { LocationViewModel() }


}