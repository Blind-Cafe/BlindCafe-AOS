package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.Retrofit.DELETE_ACCOUNT_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_PARTNER_PROFILE
import com.abouttime.blindcafe.common.constants.Retrofit.GET_PROFILE_FOR_OPEN
import com.abouttime.blindcafe.common.constants.Retrofit.GET_PROFILE_IMAGE_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_PROFILE_INFO_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_REPORTS_URL
import com.abouttime.blindcafe.common.constants.Retrofit.PATCH_PROFILE_IMAGE_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_DEVICE_TOKEN_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_PROFILE_FOR_OPEN
import com.abouttime.blindcafe.common.constants.Retrofit.POST_USER_INFO_URL
import com.abouttime.blindcafe.common.constants.Retrofit.PUT_PROFILE_INFO_URL
import com.abouttime.blindcafe.data.server.dto.user_info.DeleteAccountResponse
import com.abouttime.blindcafe.data.server.dto.user_info.GetUserInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.device_token.PostDeviceTokenDto
import com.abouttime.blindcafe.data.server.dto.user_info.edit.info.PutProfileInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.edit.info.PutProfileInfoResponse
import com.abouttime.blindcafe.data.server.dto.user_info.partner.GetPartnerProfileDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.exchange.GetProfileForOpenDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.exchange.PostProfileForOpenDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.exchange.PostProfileForOpenResponse
import com.abouttime.blindcafe.data.server.dto.user_info.profile.image.GetProfileImageDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.info.GetProfileInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.report.GetReportsDto
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
    ): BaseResponse?

    @PUT(PUT_PROFILE_INFO_URL)
    suspend fun putProfileInfo(
        @Body putProfileInfoDto: PutProfileInfoDto
    ): PutProfileInfoResponse?

    @GET(GET_PROFILE_IMAGE_URL)
    suspend fun getProfileImage(
        @Path("userId") userId: Int
    ): GetProfileImageDto?

    @Multipart
    @PATCH(PATCH_PROFILE_IMAGE_URL)
    suspend fun patchProfileImage(
        @Part("priority") priority: RequestBody,
        @Part image: MultipartBody.Part
    ): BaseResponse?


    @GET(GET_PROFILE_FOR_OPEN)
    suspend fun getProfileForOpen(
        @Path("matchingId") matchingId: Int
    ): GetProfileForOpenDto?


    @POST(POST_PROFILE_FOR_OPEN)
    suspend fun postProfileForOpen(
        @Body postProfileForOpenDto: PostProfileForOpenDto
    ): PostProfileForOpenResponse?

    @GET(GET_PARTNER_PROFILE)
    suspend fun getPartnerProfile(
        @Path("matchingId") matchingId: Int
    ): GetPartnerProfileDto?

}