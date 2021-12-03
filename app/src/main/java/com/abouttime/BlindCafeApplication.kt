package com.abouttime

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.abouttime.blindcafe.BuildConfig
import com.abouttime.blindcafe.common.constants.PreferenceKey.PREFERENCES_NAME
import com.abouttime.blindcafe.di.*
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BlindCafeApplication: Application() {


    override fun onCreate() {
        super.onCreate()


        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)

        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
//        var keyHash = Utility.getKeyHash(this)
//        Log.e("keyHash", keyHash)

        startKoin {
            androidContext((this@BlindCafeApplication))
            modules(remoteModule)
            modules(viewModelModule)
            modules(repositoryModule)
            modules(useCaseModule)
            modules(sharedPreferencesModule)
            modules(firebaseModule)
            modules(backgroundModule)
        }
    }



    companion object {
        lateinit var sharedPreferences: SharedPreferences
    }



}