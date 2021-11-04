package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.domain.use_case.PostKakaoTokenUseCase
import com.abouttime.blindcafe.domain.use_case.PostNotificationUseCase
import org.koin.dsl.module

internal val useCaseModule = module {
    factory { PostKakaoTokenUseCase(get()) }
    factory { PostNotificationUseCase(get()) }

}