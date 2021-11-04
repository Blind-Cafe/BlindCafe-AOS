package com.abouttime.blindcafe.di


import com.abouttime.blindcafe.common.constants.Url.BASE_URL
import com.abouttime.blindcafe.common.constants.Url.FIREBASE_BASE_URL
import com.abouttime.blindcafe.data.remote.AuthenticationInterceptor
import com.abouttime.blindcafe.data.remote.api.LoginApi
import com.abouttime.blindcafe.data.remote.api.NotificationApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal val remoteModule = module {


    factory { provideKakaoLoginApi(provideRetrofit()) }
    factory { provideNotificationApi(provideFirebaseRetrofit()) }

}

internal fun provideRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildOkHttpClient())
        .build()


internal fun provideFirebaseRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl(FIREBASE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildOkHttpClient())
        .build()



internal fun buildOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()

    if (BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.NONE
    }

    return OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        //.addNetworkInterceptor(AuthenticationInterceptor()) // JWT 자동 헤더 전송
        .build()
}


internal fun provideNotificationApi(retrofit: Retrofit): NotificationApi =
    retrofit.create(NotificationApi::class.java)


internal fun provideKakaoLoginApi(retrofit: Retrofit): LoginApi =
    retrofit.create(LoginApi::class.java)

