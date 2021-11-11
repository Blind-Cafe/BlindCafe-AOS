package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.data.server.api.HomeApi
import com.abouttime.blindcafe.data.server.dto.home.GetHomeInfoDto
import com.abouttime.blindcafe.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val homeApi: HomeApi
): HomeRepository {
    override suspend fun getHomeInfo(): GetHomeInfoDto {
        return homeApi.getHomeInfo()
    }
}