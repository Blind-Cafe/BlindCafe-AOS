package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.data.server.api.UserInfoApi
import com.abouttime.blindcafe.data.server.dto.user_info.GetUserInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.domain.repository.UserInfoRepository

class UserInfoRepositoryImpl(
    private val userInfoApi: UserInfoApi
): UserInfoRepository {
    override suspend fun postUserInfo(postUserInfoDto: PostUserInfoDto): BaseResponse? {
        return userInfoApi.postUserInfo(postUserInfoDto)
    }

    override suspend fun getUserInfo(): GetUserInfoDto? {
        return userInfoApi.getUserInfo()
    }

}