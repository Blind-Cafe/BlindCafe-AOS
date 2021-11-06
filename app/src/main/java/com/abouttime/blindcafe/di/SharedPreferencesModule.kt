package com.abouttime.blindcafe.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val sharedPreferencesModule = module {
    single { androidContext().getSharedPreferences("BLIND_CAFE", Context.MODE_PRIVATE) }

}