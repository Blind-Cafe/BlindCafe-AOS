package com.abouttime

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.abouttime.blindcafe.BuildConfig
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.common.constants.PreferenceKey.PREFERENCES_NAME
import com.abouttime.blindcafe.common.constants.Retrofit
import com.abouttime.blindcafe.di.*
import com.abouttime.blindcafe.di.remoteModule
import com.abouttime.blindcafe.di.repositoryModule
import com.abouttime.blindcafe.di.useCaseModule
import com.abouttime.blindcafe.di.viewModelModule
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.security.MessageDigest

class BlindCafeApplication: Application() {


    override fun onCreate() {
        super.onCreate()


        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)

        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        var keyHash = Utility.getKeyHash(this)
        Log.e("keyHash", keyHash)

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