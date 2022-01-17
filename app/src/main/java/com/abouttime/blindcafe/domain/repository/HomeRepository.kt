package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.remote.server.dto.home.GetHomeInfoDto

interface HomeRepository {
    suspend fun getHomeInfo(): GetHomeInfoDto
}