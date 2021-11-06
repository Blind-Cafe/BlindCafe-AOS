package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.data.repository.FirestorageRepositoryImpl
import com.abouttime.blindcafe.data.repository.FirestoreRepositoryImpl
import com.abouttime.blindcafe.data.repository.LoginRepositoryImpl
import com.abouttime.blindcafe.data.repository.NotificationRepositoryImpl
import com.abouttime.blindcafe.domain.repository.FirestorageRepository
import com.abouttime.blindcafe.domain.repository.FirestoreRepository
import com.abouttime.blindcafe.domain.repository.LoginRepository
import com.abouttime.blindcafe.domain.repository.NotificationRepository
import org.koin.dsl.module


internal val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) } // factory 로 바꿔볼까?
    single<NotificationRepository> { NotificationRepositoryImpl(get()) }
    single<FirestoreRepository> { FirestoreRepositoryImpl(get()) }
    single<FirestorageRepository> { FirestorageRepositoryImpl(get())}
}