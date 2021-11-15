package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.server.dto.matching.PostDrinkResponse
import com.abouttime.blindcafe.data.server.dto.matching.PostMatchingRequestResponse

interface MatchingRepository {

    suspend fun postMatchingRequest(): PostMatchingRequestResponse?
    suspend fun postDrink(matchingId: Int): PostDrinkResponse?
}