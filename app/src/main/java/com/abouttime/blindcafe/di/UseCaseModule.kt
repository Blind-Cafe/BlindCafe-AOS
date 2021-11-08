package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.domain.use_case.*
import org.koin.dsl.module

internal val useCaseModule = module {
    factory { PostKakaoTokenUseCase(get()) }
    factory { PostNotificationUseCase(get()) }
    factory { SendMessageUseCase(get()) }
    factory { ReceiveMessageUseCase(get()) }
    factory { UploadAudioUseCase(get()) }
    factory { UploadImageUseCase(get()) }
    factory { DownloadAudioUrlUseCase(get()) }
    factory { DownloadImageUrlUseCase(get()) }
    factory { GetUserInfoUseCase(get()) }
    factory { PostUserInfoUseCase(get()) }

}