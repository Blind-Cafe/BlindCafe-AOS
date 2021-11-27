package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.data.server.api.UserInfoApi
import com.abouttime.blindcafe.data.server.dto.user_info.DeleteAccountResponse
import com.abouttime.blindcafe.data.server.dto.user_info.GetUserInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.device_token.PostDeviceTokenDto
import com.abouttime.blindcafe.data.server.dto.user_info.edit.PutProfileInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.edit.PutProfileInfoResponse
import com.abouttime.blindcafe.data.server.dto.user_info.profile.GetProfileInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.report.GetReportsDto
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

    override suspend fun deleteAccount(reason: Int): DeleteAccountResponse? {
        return userInfoApi.deleteAccount(reason)
    }

    override suspend fun getReports(): GetReportsDto? {
        return userInfoApi.getReports()
    }

    override suspend fun getProfileInfo(): GetProfileInfoDto? {
        return userInfoApi.getProfileInfo()
    }

    override suspend fun postDeviceToken(postDeviceTokenDto: PostDeviceTokenDto): BaseResponse? {
        return userInfoApi.postDeviceToken(postDeviceTokenDto)
    }

    override suspend fun putProfileInfo(putProfileInfoDto: PutProfileInfoDto): PutProfileInfoResponse? {
        return userInfoApi.putProfileInfo(putProfileInfoDto)
    }
}