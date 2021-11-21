package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.data.gallery.ImageDataSource
import com.abouttime.blindcafe.data.gallery.ImageDataSourceFactory
import com.abouttime.blindcafe.data.gallery.MediaStoreAdapter
import com.abouttime.blindcafe.data.repository.*
import com.abouttime.blindcafe.domain.repository.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


internal val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) } // factory 로 바꿔볼까?
    single<NotificationRepository> { NotificationRepositoryImpl(get()) }
    single<FirestoreRepository> { FirestoreRepositoryImpl(get()) }
    single<FirestorageRepository> { FirestorageRepositoryImpl(get())}
    single<UserInfoRepository> { UserInfoRepositoryImpl(get()) }
    factory<ImageRepository> { ImageRepositoryImpl(androidContext()) }
    single<FcmRepository> { FcmRepositoryImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get())}
    single<MatchingRepository> { MatchingRepositoryImpl(get()) }
    single<InterestRepository> { InterestRepositoryImpl(get())}

}