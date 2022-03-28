package com.abouttime.blindcafe.di

import com.abouttime.blindcafe.domain.use_case.firebase.*
import com.abouttime.blindcafe.domain.use_case.server.*
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


    /** fire store **/
    factory { SendMessageUseCase(get()) }
    factory { SubscribeMessageUseCase(get()) }
    factory { ReceiveMessagesUseCase(get()) }

    /** firebase storage **/
    factory { UploadAudioUseCase(get()) }
    factory { UploadImageUseCase(get()) }
    factory { DownloadAudioUrlUseCase(get()) }
    factory { DownloadImageUrlUseCase(get()) }




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

}