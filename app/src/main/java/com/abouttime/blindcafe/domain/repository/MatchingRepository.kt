package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.data.server.dto.matching.*
import com.abouttime.blindcafe.data.server.dto.matching.topic.GetTopicDto

interface MatchingRepository {

    suspend fun postMatchingRequest(): PostMatchingRequestResponse?
    suspend fun postDrink(matchingId: Int, drink: PostDrinkDto): PostDrinkResponse?
    suspend fun getChatRooms(): GetChatRoomsDto?
    suspend fun getChatRoomInfo(matchingId: Int): GetChatRoomInfoDto?
    suspend fun exitChatRoom(matchingId: Int, reason: Int): BaseResponse?
    suspend fun getTopic(matchingId: Int): GetTopicDto?
}