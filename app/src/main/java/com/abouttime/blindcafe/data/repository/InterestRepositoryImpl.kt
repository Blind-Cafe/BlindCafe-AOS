package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.data.server.api.InterestApi
import com.abouttime.blindcafe.data.server.dto.interest.GetInterestResponse
import com.abouttime.blindcafe.data.server.dto.interest.PostInterestDto
import com.abouttime.blindcafe.domain.repository.InterestRepository

class InterestRepositoryImpl(
    private val interestApi: InterestApi
): InterestRepository {
    override suspend fun getInterests(id1: Int, id2:Int, id3: Int): GetInterestResponse? {
        return interestApi.getInterests(
            id1,
            id2,
            id3
        )
    }

    override suspend fun postInterests(postInterestDto: PostInterestDto): BaseResponse? {
        return interestApi.postInterests(postInterestDto)
    }
}