package com.abouttime

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.abouttime.blindcafe.BuildConfig
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.common.constants.Retrofit
import com.abouttime.blindcafe.di.*
import com.abouttime.blindcafe.di.remoteModule
import com.abouttime.blindcafe.di.repositoryModule
import com.abouttime.blindcafe.di.useCaseModule
import com.abouttime.blindcafe.di.viewModelModule
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BlindCafeApplication: Application() {


    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)

        sharedPreferences = getSharedPreferences("BLIND_CAFE", Context.MODE_PRIVATE)
        var keyHash = Utility.getKeyHash(this)
        Log.e("keyHash", keyHash)

        FirebaseMessaging.getInstance().subscribeToTopic(Retrofit.FCM_MESSAGE_TOPIC).addOnCompleteListener { task ->
            var msg = "구독 성공"
            if (!task.isSuccessful) {
                msg = "구독 실패"
            }
            Log.d(LogTag.FCM_TAG, msg)
            //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        }

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