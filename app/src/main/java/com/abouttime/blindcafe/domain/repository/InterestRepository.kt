package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.server.dto.interest.GetInterestResponse

interface InterestRepository {
    suspend fun getInterests(id1: Int, id2:Int, id3: Int): GetInterestResponse?
}