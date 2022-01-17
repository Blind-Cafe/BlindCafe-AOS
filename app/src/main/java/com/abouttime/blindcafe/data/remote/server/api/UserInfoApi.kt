package com.abouttime.blindcafe.data.remote.server.api

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.Retrofit.DELETE_ACCOUNT_URL
import com.abouttime.blindcafe.common.constants.Retrofit.DELETE_PROFILE_IMAGE_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_MATCHING_PROFILE
import com.abouttime.blindcafe.common.constants.Retrofit.GET_MY_PROFILE_IMAGE_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_PARTNER_PROFILE_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_PROFILE_FOR_OPEN_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_PROFILE_IMAGE_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_PROFILE_INFO_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_REPORTS_URL
import com.abouttime.blindcafe.common.constants.Retrofit.PATCH_PROFILE_IMAGE_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_DEVICE_TOKEN_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_PROFILE_FOR_OPEN_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_USER_INFO_URL
import com.abouttime.blindcafe.common.constants.Retrofit.PUT_PROFILE_INFO_URL
import com.abouttime.blindcafe.data.remote.server.dto.user_info.DeleteAccountResponse
import com.abouttime.blindcafe.data.remote.server.dto.user_info.GetUserInfoDto
import com.abouttime.blindcafe.data.remote.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.data.remote.server.dto.user_info.device_token.PostDeviceTokenDto
import com.abouttime.blindcafe.data.remote.server.dto.user_info.edit.info.PutProfileInfoDto
import com.abouttime.blindcafe.data.remote.server.dto.user_info.edit.info.PutProfileInfoResponse
import com.abouttime.blindcafe.data.remote.server.dto.user_info.partner.GetPartnerProfileDto
import com.abouttime.blindcafe.data.remote.server.dto.user_info.partner.matched.GetMatchingProfileDto
import com.abouttime.blindcafe.data.remote.server.dto.user_info.profile.exchange.GetProfileForOpenDto
import com.abouttime.blindcafe.data.remote.server.dto.user_info.profile.exchange.PostProfileForOpenDto
import com.abouttime.blindcafe.data.remote.server.dto.user_info.profile.exchange.PostProfileForOpenResponse
import com.abouttime.blindcafe.data.remote.server.dto.user_info.profile.image.GetProfileImageDto
import com.abouttime.blindcafe.data.remote.server.dto.user_info.profile.info.GetProfileInfoDto
import com.abouttime.blindcafe.data.remote.server.dto.user_info.report.GetReportsDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UserInfoApi {
    @POST(POST_USER_INFO_URL)
    suspend fun postUserInfo(
        @Body postUserInfoDto: PostUserInfoDto
    ): BaseResponse


    @GET(POST_USER_INFO_URL)
    suspend fun getUserInfo(): GetUserInfoDto

    @DELETE(DELETE_ACCOUNT_URL)
    suspend fun deleteAccount(@Query("reason") reason: Int): DeleteAccountResponse?

    @GET(GET_REPORTS_URL)
    suspend fun getReports(): GetReportsDto?

    @GET(GET_PROFILE_INFO_URL)
    suspend fun getProfileInfo(): GetProfileInfoDto?


    @PATCH(POST_DEVICE_TOKEN_URL)
    suspend fun postDeviceToken(
        @Body postDeviceTokenDto: PostDeviceTokenDto
    )

    @PUT(PUT_PROFILE_INFO_URL)
    suspend fun putProfileInfo(
        @Body putProfileInfoDto: PutProfileInfoDto
    ): PutProfileInfoResponse?

    @GET(GET_PROFILE_IMAGE_URL)
    suspend fun getProfileImage(
        @Path("userId") userId: Int
    ): GetProfileImageDto?

    @GET(GET_MY_PROFILE_IMAGE_URL)
    suspend fun getMyProfileImage(): GetProfileImageDto?

    @Multipart
    @PATCH(PATCH_PROFILE_IMAGE_URL)
    suspend fun patchProfileImage(
        @Part("priority") priority: RequestBody,
        @Part image: MultipartBody.Part?
    )


    @GET(GET_PROFILE_FOR_OPEN_URL)
    suspend fun getProfileForOpen(
        @Path("matchingId") matchingId: Int
    ): GetProfileForOpenDto?


    @POST(POST_PROFILE_FOR_OPEN_URL)
    suspend fun postProfileForOpen(
        @Path("matchingId") matchingId: Int,
        @Body postProfileForOpenDto: PostProfileForOpenDto
    ): PostProfileForOpenResponse?

    @GET(GET_PARTNER_PROFILE_URL)
    suspend fun getPartnerProfile(
        @Path("matchingId") matchingId: Int
    ): GetPartnerProfileDto?

    @DELETE(DELETE_PROFILE_IMAGE_URL)
    suspend fun deleteProfileImage(
        @Query("priority") priority: Int
    )

    @GET(GET_MATCHING_PROFILE)
    suspend fun getMatchingProfile(
        @Path("userId") userId: Int
    ): GetMatchingProfileDto?


}