package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.Retrofit.DELETE_ACCOUNT_URL
import com.abouttime.blindcafe.common.constants.Retrofit.USER_INFO_URL
import com.abouttime.blindcafe.data.server.dto.user_info.DeleteAccountResponse
import com.abouttime.blindcafe.data.server.dto.user_info.GetUserInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import retrofit2.http.*

interface UserInfoApi {
    @POST(USER_INFO_URL)
    suspend fun postUserInfo(
        @Body postUserInfoDto: PostUserInfoDto
    ): BaseResponse


    @GET(USER_INFO_URL)
    suspend fun getUserInfo(): GetUserInfoDto

    @DELETE(DELETE_ACCOUNT_URL)
    suspend fun deleteAccount(@Query("reason") reason: Int): DeleteAccountResponse?
}