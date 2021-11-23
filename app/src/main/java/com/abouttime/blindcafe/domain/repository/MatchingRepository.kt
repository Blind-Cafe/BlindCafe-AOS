package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.data.server.dto.matching.*

interface MatchingRepository {

    suspend fun postMatchingRequest(): PostMatchingRequestResponse?
    suspend fun postDrink(matchingId: Int, drink: PostDrinkDto): PostDrinkResponse?
    suspend fun getChatRooms(): GetChatRoomsDto?
    suspend fun getChatRoomInfo(matchingId: Int): GetChatRoomInfoDto?
}