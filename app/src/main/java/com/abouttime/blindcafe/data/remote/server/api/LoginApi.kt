package com.abouttime.blindcafe.data.remote.server.api

import com.abouttime.blindcafe.common.constants.Retrofit.POST_KAKAO_LOGIN_URL
import com.abouttime.blindcafe.data.remote.server.dto.login.KakaoTokenDto
import com.abouttime.blindcafe.data.remote.server.dto.login.KakaoTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST(POST_KAKAO_LOGIN_URL)
    suspend fun postKakaoAccessToken(
        @Body kakaoAccessTokenDto: KakaoTokenDto
    ): KakaoTokenResponse?
}