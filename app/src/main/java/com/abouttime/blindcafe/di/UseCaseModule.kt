package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.domain.use_case.*
import org.koin.dsl.module

internal val useCaseModule = module {
    /** login **/
    factory { PostKakaoTokenUseCase(get()) }


    /** user info **/
    factory { GetUserInfoUseCase(get()) }
    factory { PostUserInfoUseCase(get()) }
    factory { DeleteAccountUseCase(get()) }
    factory { GetReportsUseCase(get())}
    factory { GetProfileInfoUseCase(get()) }

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
    factory { PostFcmUseCase(get()) }


    /** home **/
    factory { GetHomeInfoUseCase(get()) }

    /** matching **/
    factory { PostMatchingRequestUseCase(get()) }
    factory { PostDrinkUseCase(get()) }
    factory { GetChatRoomsUseCase(get())}
    factory { GetChatRoomInfoUseCase(get()) }
    factory { DeleteExitChatRoomUseCase(get())}
    factory { GetTopicUseCase(get())}
    factory { PostReportUseCase(get()) }
    factory { PostCancelMatchingUseCase(get()) }


    /** interests **/
    factory { GetInterestUseCase(get()) }

}