package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.data.server.api.UserInfoApi
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
import com.abouttime.blindcafe.domain.repository.UserInfoRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    override suspend fun getProfileImage(userId: Int): GetProfileImageDto? {
        return userInfoApi.getProfileImage(userId)
    }

    override suspend fun patchProfileImage(priority: RequestBody, image: MultipartBody.Part): BaseResponse? {
        return userInfoApi.patchProfileImage(priority, image)
    }

    override suspend fun getProfileForOpen(matchingId: Int): GetProfileForOpenDto? {
        return userInfoApi.getProfileForOpen(matchingId)
    }

    override suspend fun postProfileForOpen(postProfileForOpenDto: PostProfileForOpenDto): PostProfileForOpenResponse? {
        return userInfoApi.postProfileForOpen(postProfileForOpenDto)
    }

    override suspend fun getPartnerProfile(matchingId: Int): GetPartnerProfileDto? {
        return userInfoApi.getPartnerProfile(matchingId)
    }
}