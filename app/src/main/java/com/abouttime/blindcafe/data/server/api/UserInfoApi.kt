package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.Retrofit.DELETE_ACCOUNT_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_PROFILE_INFO
import com.abouttime.blindcafe.common.constants.Retrofit.GET_REPORTS_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_USER_INFO_URL
import com.abouttime.blindcafe.data.server.dto.user_info.DeleteAccountResponse
import com.abouttime.blindcafe.data.server.dto.user_info.GetUserInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.GetProfileInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.report.GetReportsDto
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

    @GET(GET_PROFILE_INFO)
    suspend fun getProfileInfo(): GetProfileInfoDto?
}