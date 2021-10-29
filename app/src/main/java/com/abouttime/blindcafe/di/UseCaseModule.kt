package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.domain.use_case.PostKakaoTokenUseCase
import org.koin.dsl.module

internal val useCaseModule = module {
    factory { PostKakaoTokenUseCase(get()) }

}