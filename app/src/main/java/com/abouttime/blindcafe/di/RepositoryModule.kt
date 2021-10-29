package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.data.repository.LoginRepositoryImpl
import com.abouttime.blindcafe.domain.repository.LoginRepository
import org.koin.dsl.module


internal val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) } // factory 로 바꿔볼까?
}