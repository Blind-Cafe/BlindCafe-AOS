package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.server.api.HomeApi
import com.abouttime.blindcafe.data.server.dto.home.GetHomeInfoDto

interface HomeRepository {
    suspend fun getHomeInfo(): GetHomeInfoDto
}