package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.data.server.dto.user_info.DeleteAccountResponse
import com.abouttime.blindcafe.data.server.dto.user_info.GetUserInfoDto
import com.abouttime.blindcafe.data.server.dto.user_info.PostUserInfoDto

interface UserInfoRepository {
    suspend fun postUserInfo(postUserInfoDto: PostUserInfoDto): BaseResponse?
    suspend fun getUserInfo(): GetUserInfoDto?
    suspend fun deleteAccount(reason: Int): DeleteAccountResponse?
}