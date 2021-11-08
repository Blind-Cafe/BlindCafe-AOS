package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.Retrofit.USER_INFO_URL
import com.abouttime.blindcafe.data.server.dto.GetUserInfoDto
import com.abouttime.blindcafe.data.server.dto.PostUserInfoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserInfoApi {
    @POST(USER_INFO_URL)
    suspend fun postUserInfo(
        @Body postUserInfoDto: PostUserInfoDto
    ): BaseResponse


    @GET(USER_INFO_URL)
    suspend fun getUserInfo(): GetUserInfoDto
}