package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.constants.Url
import com.abouttime.blindcafe.data.server.dto.KakaoToken
import com.abouttime.blindcafe.data.server.dto.KakaoTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST(Url.KAKAO_LOGIN)
    suspend fun postKakaoAccessToken(
        @Body kakaoAccessToken: KakaoToken
    ): KakaoTokenResponse
}