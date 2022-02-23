package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.domain.use_case.local.database.InsertMessageUseCase
import com.abouttime.blindcafe.domain.use_case.local.database.LoadMessageUseCase
import com.abouttime.blindcafe.domain.use_case.local.media_store.GetGalleryImagesUseCase
import com.abouttime.blindcafe.domain.use_case.remote.firebase.*
import com.abouttime.blindcafe.domain.use_case.remote.server.*
import org.koin.dsl.module

internal val useCaseModule = module {
    /** login **/
    factory { PostKakaoTokenUseCase(get()) }


    /** user info **/
    factory { GetUserInfoUseCase(get()) }
    factory { PostUserInfoUseCase(get()) }
    factory { DeleteAccountUseCase(get()) }
    factory { GetReportsUseCase(get()) }
    factory { GetProfileInfoUseCase(get()) }
    factory { PostDeviceTokenUseCase(get()) }
    factory { PutProfileInfoUseCase(get())}
    factory { PatchProfileImageUseCase(get()) }
    factory { GetProfileImageUseCase(get()) }
    factory { GetProfileForOpenUseCase(get()) }
    factory { GetPartnerProfileUseCase(get()) }
    factory { GetMyProfileImageUseCase(get())}
    factory { DeleteProfileImageUseCase(get()) }
    factory { PostProfileForOpenUseCase(get()) }
    factory { GetMatchingProfileUseCase(get()) }

    /** gallery **/
    factory { GetGalleryImagesUseCase(get()) }

    /** fire store **/
    factory { SendMessageUseCase(get()) }
    factory { SubscribeMessageUseCase(get()) }
    factory { ReceiveMessagesUseCase(get()) }

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
    factory { GetChatRoomsUseCase(get()) }
    factory { GetChatRoomInfoUseCase(get()) }
    factory { DeleteExitChatRoomUseCase(get()) }
    factory { GetTopicUseCase(get()) }
    factory { PostReportUseCase(get()) }
    factory { PostCancelMatchingUseCase(get()) }
    factory { PostAcceptMatchingUseCase(get()) }
    factory { DeleteDismissMatchingUseCase(get()) }
    factory { PostEnteringLogUseCase(get()) }
    factory { PostMessageUseCase(get())}


    /** interests **/
    factory { GetInterestUseCase(get()) }
    factory { PostInterestsUseCase(get())}


    /** local **/
    factory { InsertMessageUseCase(get())}
    factory { LoadMessageUseCase(get()) }

}