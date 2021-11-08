package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.data.server.dto.GetUserInfoDto
import com.abouttime.blindcafe.data.server.dto.PostUserInfoDto

interface UserInfoRepository {
    suspend fun postUserInfo(postUserInfoDto: PostUserInfoDto): BaseResponse?
    suspend fun getUserInfo(): GetUserInfoDto?
}