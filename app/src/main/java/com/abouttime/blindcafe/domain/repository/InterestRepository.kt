package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.data.remote.server.dto.interest.GetInterestResponse
import com.abouttime.blindcafe.data.remote.server.dto.interest.PostInterestDto

interface InterestRepository {
    suspend fun getInterests(id1: Int, id2:Int, id3: Int): GetInterestResponse?
    suspend fun postInterests(postInterestDto: PostInterestDto): BaseResponse?
}