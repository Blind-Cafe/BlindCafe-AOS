package com.abouttime

import android.app.Application
import com.abouttime.blindcafe.BuildConfig
import com.abouttime.blindcafe.di.remoteModule
import com.abouttime.blindcafe.di.viewModelModule
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BlindCafeApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)


        startKoin {
            androidContext((this@BlindCafeApplication))
            modules(remoteModule)
            modules(viewModelModule)
        }


    }

}