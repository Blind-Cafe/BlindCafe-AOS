package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.common.base.BaseResponse
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
import retrofit2.Call

interface UserInfoRepository {
    suspend fun postUserInfo(postUserInfoDto: PostUserInfoDto): BaseResponse?
    suspend fun getUserInfo(): GetUserInfoDto?
    suspend fun deleteAccount(reason: Int): DeleteAccountResponse?
    suspend fun getReports(): GetReportsDto?
    suspend fun getProfileInfo(): GetProfileInfoDto?
    suspend fun postDeviceToken(postDeviceTokenDto: PostDeviceTokenDto)
    suspend fun putProfileInfo(putProfileInfoDto: PutProfileInfoDto): PutProfileInfoResponse?
    suspend fun getProfileImage(userId: Int): GetProfileImageDto?
    suspend fun getMyProfileImage(): GetProfileImageDto?
    suspend fun patchProfileImage(priority: RequestBody, image: MultipartBody.Part?)
    suspend fun getProfileForOpen(matchingId: Int): GetProfileForOpenDto?
    suspend fun postProfileForOpen(matchingId: Int, postProfileForOpenDto: PostProfileForOpenDto): PostProfileForOpenResponse?
    suspend fun getPartnerProfile(matchingId: Int): GetPartnerProfileDto?
    suspend fun deleteProfileImage(priority: Int)

}