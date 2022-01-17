package com.abouttime.blindcafe.data.remote.server.api

import com.abouttime.blindcafe.common.constants.Retrofit.HOME_INFO_URL
import com.abouttime.blindcafe.data.remote.server.dto.home.GetHomeInfoDto
import retrofit2.http.GET

interface HomeApi {
    @GET(HOME_INFO_URL)
    suspend fun getHomeInfo(): GetHomeInfoDto
}