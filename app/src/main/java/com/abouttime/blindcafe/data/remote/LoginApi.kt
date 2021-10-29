package com.abouttime.blindcafe.data.remote

import com.abouttime.blindcafe.common.base.response.BaseResponse
import com.abouttime.blindcafe.common.constants.Url
import com.abouttime.blindcafe.data.remote.dto.KakaoToken
import com.abouttime.blindcafe.data.remote.dto.KakaoTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST(Url.KAKAO_LOGIN)
    suspend fun postKakaoAccessToken(
        @Body kakaoAccessToken: KakaoToken
    ): KakaoTokenResponse
}