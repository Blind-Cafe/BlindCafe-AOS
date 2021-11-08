package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.constants.Retrofit
import com.abouttime.blindcafe.common.constants.Retrofit.POST_KAKAO_LOGIN_URL
import com.abouttime.blindcafe.data.server.dto.KakaoTokenDto
import com.abouttime.blindcafe.data.server.dto.KakaoTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST(POST_KAKAO_LOGIN_URL)
    suspend fun postKakaoAccessToken(
        @Body kakaoAccessTokenDto: KakaoTokenDto
    ): KakaoTokenResponse?
}