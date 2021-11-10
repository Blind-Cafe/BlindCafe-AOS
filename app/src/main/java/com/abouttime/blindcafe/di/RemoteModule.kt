package com.abouttime.blindcafe.di


import com.abouttime.blindcafe.common.AuthenticationInterceptor
import com.abouttime.blindcafe.common.constants.Retrofit.BASE_URL
import com.abouttime.blindcafe.common.constants.Retrofit.FIREBASE_BASE_URL
import com.abouttime.blindcafe.data.server.api.FcmApi
import com.abouttime.blindcafe.data.server.api.LoginApi
import com.abouttime.blindcafe.data.server.api.NotificationApi
import com.abouttime.blindcafe.data.server.api.UserInfoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal val remoteModule = module {


    factory { provideKakaoLoginApi(provideRetrofit()) }
    factory { provideUserInfoApi(provideRetrofit()) }
    factory { provideFcmApi(provideRetrofit()) }




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
        .client(buildFirebaseOkHttpClient())
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
        .addNetworkInterceptor(AuthenticationInterceptor()) // JWT 자동 헤더 전송
        .build()
}

internal fun buildFirebaseOkHttpClient(): OkHttpClient {
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
        .build()
}



internal fun provideNotificationApi(retrofit: Retrofit): NotificationApi =
    retrofit.create(NotificationApi::class.java)


internal fun provideKakaoLoginApi(retrofit: Retrofit): LoginApi =
    retrofit.create(LoginApi::class.java)

internal fun provideUserInfoApi(retrofit: Retrofit): UserInfoApi =
    retrofit.create(UserInfoApi::class.java)

internal fun provideFcmApi(retrofit: Retrofit): FcmApi =
        retrofit.create(FcmApi::class.java)

