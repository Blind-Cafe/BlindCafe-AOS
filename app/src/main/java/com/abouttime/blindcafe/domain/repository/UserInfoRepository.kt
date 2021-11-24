package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.data.server.dto.user_info.DeleteAccountResponse
import com.abouttime.blindcafe.data.server.dto.user_info.GetUserInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.profile.GetProfileInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.report.GetReportsDto

interface UserInfoRepository {
    suspend fun postUserInfo(postUserInfoDto: PostUserInfoDto): BaseResponse?
    suspend fun getUserInfo(): GetUserInfoDto?
    suspend fun deleteAccount(reason: Int): DeleteAccountResponse?
    suspend fun getReports(): GetReportsDto?
    suspend fun getProfileInfo(): GetProfileInfoDto?
}