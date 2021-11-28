package com.abouttime.blindcafe.common.constants

object Retrofit {
    private const val PROD_BASE_URL = "https://www.blindcafe.me/"
    private const val DEV_BASE_URL = "http://dev.blindcafe.me/"
    const val BASE_URL = PROD_BASE_URL


    const val JWT = "jwt"
    const val HEADER = "X-ACCESS-TOKEN"
    const val USER_ID = "User ID"

    const val POST_KAKAO_LOGIN_URL = "api/kakao"
    const val POST_USER_INFO_URL = "api/user"
    const val FCM_URL = "fcm"
    const val HOME_INFO_URL = "api/user/home"
    const val POST_MATCHING_REQUEST_URL = "api/matching"
    const val POST_DRINK_URL = "api/matching/{matchingId}/drink"
    const val GET_INTEREST_URL = "api/interest"
    const val DELETE_ACCOUNT_URL = "api/user"
    const val GET_CHAT_ROOMS_URL = "api/matching"
    const val GET_CHAT_ROOM_INFO_URL = "api/matching/{matchingId}"
    const val DELETE_EXIT_CHAT_ROOM_URL = "api/matching/{matchingId}"
    const val GET_TOPIC_URL = "api/matching/{matchingId}/topic"
    const val POST_REPORT_URL = "api/report"
    const val GET_REPORTS_URL = "api/report"
    const val POST_CANCEL_MATCHING_URL = "api/matching/cancel"
    const val GET_PROFILE_INFO_URL = "api/user/profile"
    const val POST_DEVICE_TOKEN_URL = "api/user/device"
    const val PUT_INTEREST_URL = "api/user/interest"
    const val PUT_PROFILE_INFO_URL = "api/user"
    const val PATCH_PROFILE_IMAGE_URL = "api/user/image"
    const val GET_PROFILE_IMAGE_URL = "api/user/{userId}/image" // 28
    const val GET_MY_PROFILE_IMAGE_URL = "api/user/image" // 27
    const val GET_PROFILE_FOR_OPEN_URL = "api/matching/{matchingId}/profile"
    const val POST_PROFILE_FOR_OPEN_URL = "api/matching/{matchingId}/profile"
    const val GET_PARTNER_PROFILE_URL = "api/matching/{matchingId}/partner"
    const val POST_ACCEPT_MATCHING_URL = "api/matching/{matchingId}/partner" // 30
    const val DELETE_DISMISS_MATCHING_URL ="api/matching/{matchingId}/partner" // 31
    const val DELETE_PROFILE_IMAGE_URL = "api/user/image" // 33
    const val POST_ENTERING_LOG_URL = "api/matching/{matchingId}/log"



    const val FCM_MESSAGE_TOPIC = "fcm_message_topic"


}