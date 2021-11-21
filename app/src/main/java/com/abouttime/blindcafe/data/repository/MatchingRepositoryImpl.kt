package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.data.server.api.MatchingApi
import com.abouttime.blindcafe.data.server.dto.matching.PostDrinkDto
import com.abouttime.blindcafe.data.server.dto.matching.PostDrinkResponse
import com.abouttime.blindcafe.data.server.dto.matching.PostMatchingRequestResponse
import com.abouttime.blindcafe.domain.repository.MatchingRepository

class MatchingRepositoryImpl(
    private val matchingApi: MatchingApi
): MatchingRepository {
    override suspend fun postMatchingRequest(): PostMatchingRequestResponse? {
        return matchingApi.postMatchingRequest()
    }

    override suspend fun postDrink(matchingId: Int, drink: PostDrinkDto): PostDrinkResponse? {
       return matchingApi.postDrink(matchingId, drink)
    }
}