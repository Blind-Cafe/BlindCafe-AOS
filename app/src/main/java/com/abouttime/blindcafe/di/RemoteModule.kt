package com.abouttime.blindcafe.di


import com.abouttime.blindcafe.common.util.AuthenticationInterceptor
import com.abouttime.blindcafe.common.constants.Retrofit.BASE_URL
import com.abouttime.blindcafe.data.remote.server.api.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal val remoteModule = module {

    single { provideRetrofit() }

    factory { provideKakaoLoginApi(get()) }
    factory { provideUserInfoApi(get()) }
    factory { provideFcmApi(get()) }
    factory { provideHomeApi(get()) }
    factory { provideMatchingApi(get()) }
    factory { provideInterestApi(get()) }

}

internal fun provideRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildOkHttpClient())
        .build()


internal fun buildOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()

    if (BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        interceptor.level = HttpLoggingInterceptor.Level.BODY // TODO NONE 으로 다시 바꿀 것
    }

    return OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addNetworkInterceptor(AuthenticationInterceptor()) // JWT 자동 헤더 전송
        .build()
}



internal fun provideKakaoLoginApi(retrofit: Retrofit): LoginApi =
    retrofit.create(LoginApi::class.java)

internal fun provideUserInfoApi(retrofit: Retrofit): UserInfoApi =
    retrofit.create(UserInfoApi::class.java)

internal fun provideFcmApi(retrofit: Retrofit): FcmApi =
        retrofit.create(FcmApi::class.java)

internal fun provideHomeApi(retrofit: Retrofit): HomeApi =
    retrofit.create(HomeApi::class.java)

internal fun provideMatchingApi(retrofit: Retrofit): MatchingApi =
    retrofit.create(MatchingApi::class.java)

internal fun provideInterestApi(retrofit: Retrofit): InterestApi =
    retrofit.create(InterestApi::class.java)

