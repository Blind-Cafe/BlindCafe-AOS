package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.data.repository.*
import com.abouttime.blindcafe.domain.repository.*
import org.koin.dsl.module


internal val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) } // factory 로 바꿔볼까?
    single<NotificationRepository> { NotificationRepositoryImpl(get()) }
    single<FirestoreRepository> { FirestoreRepositoryImpl(get()) }
    single<FirestorageRepository> { FirestorageRepositoryImpl(get())}
    single<UserInfoRepository> { UserInfoRepositoryImpl(get()) }
}