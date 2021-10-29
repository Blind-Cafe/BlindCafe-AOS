package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.presentation.main.MainViewModel
import com.abouttime.blindcafe.presentation.onboarding.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { LoginViewModel(get()) }

}