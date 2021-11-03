package com.abouttime

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import com.abouttime.blindcafe.BuildConfig
import com.abouttime.blindcafe.di.remoteModule
import com.abouttime.blindcafe.di.repositoryModule
import com.abouttime.blindcafe.di.useCaseModule
import com.abouttime.blindcafe.di.viewModelModule
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.prefs.Preferences

class BlindCafeApplication: Application() {


    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)

        sharedPreferences = getSharedPreferences("BLIND_CAFE", Context.MODE_PRIVATE)

        startKoin {
            androidContext((this@BlindCafeApplication))
            modules(remoteModule)
            modules(viewModelModule)
            modules(repositoryModule)
            modules(useCaseModule)
        }


    }

    companion object {
        lateinit var sharedPreferences: SharedPreferences
    }

}