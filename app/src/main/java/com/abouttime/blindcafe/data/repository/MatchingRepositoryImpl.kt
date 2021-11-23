package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.data.server.api.MatchingApi
import com.abouttime.blindcafe.data.server.dto.matching.*
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

    override suspend fun getChatRooms(): GetChatRoomsDto? {
        return matchingApi.getChatRooms()
    }

    override suspend fun getChatRoomInfo(matchingId: Int): GetChatRoomInfoDto? {
        return matchingApi.getChatRoomInfo(matchingId)
    }
}