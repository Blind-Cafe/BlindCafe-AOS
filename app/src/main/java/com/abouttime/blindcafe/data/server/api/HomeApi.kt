package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.constants.Retrofit.HOME_INFO_URL
import com.abouttime.blindcafe.data.server.dto.home.GetHomeInfoDto
import retrofit2.http.GET

interface HomeApi {
    @GET(HOME_INFO_URL)
    suspend fun getHomeInfo(): GetHomeInfoDto
}