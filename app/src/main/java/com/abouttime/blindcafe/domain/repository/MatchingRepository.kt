package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.server.dto.matching.GetChatRooms
import com.abouttime.blindcafe.data.server.dto.matching.PostDrinkDto
import com.abouttime.blindcafe.data.server.dto.matching.PostDrinkResponse
import com.abouttime.blindcafe.data.server.dto.matching.PostMatchingRequestResponse

interface MatchingRepository {

    suspend fun postMatchingRequest(): PostMatchingRequestResponse?
    suspend fun postDrink(matchingId: Int, drink: PostDrinkDto): PostDrinkResponse?
    suspend fun getChatRooms(): GetChatRooms?
}