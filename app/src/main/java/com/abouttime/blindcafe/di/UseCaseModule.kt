package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.domain.use_case.*
import org.koin.dsl.module

internal val useCaseModule = module {
    /** login **/
    factory { PostKakaoTokenUseCase(get()) }


    /** user info **/
    factory { GetUserInfoUseCase(get()) }
    factory { PostUserInfoUseCase(get()) }

    /** gallery **/
    factory { FetchImagesUseCase(get())}

    /** fire store **/
    factory { SendMessageUseCase(get()) }
    factory { ReceiveMessageUseCase(get()) }

    /** firebase storage **/
    factory { UploadAudioUseCase(get()) }
    factory { UploadImageUseCase(get()) }
    factory { DownloadAudioUrlUseCase(get()) }
    factory { DownloadImageUrlUseCase(get()) }

    /** fcm **/
    factory { PostNotificationUseCase(get()) } // firebase api
    factory { PostFcmUseCase(get()) }


    /** home **/
    factory { GetHomeInfoUseCase(get()) }

}