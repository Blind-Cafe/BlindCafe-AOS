package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.constants.Retrofit.GET_CHAT_ROOMS_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_CHAT_ROOM_INFO_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_DRINK_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_MATCHING_REQUEST_URL
import com.abouttime.blindcafe.data.server.dto.matching.*
import retrofit2.http.*

interface MatchingApi {
    @POST(POST_MATCHING_REQUEST_URL)
    suspend fun postMatchingRequest(): PostMatchingRequestResponse?

    @POST(POST_DRINK_URL)
    suspend fun postDrink(
        @Path("matchingId") matchingId: Int,
        @Body drink: PostDrinkDto
    ): PostDrinkResponse?


    @GET(GET_CHAT_ROOMS_URL)
    suspend fun getChatRooms(): GetChatRoomsDto?

    @GET(GET_CHAT_ROOM_INFO_URL)
    suspend fun getChatRoomInfo(
        @Path("matchingId") matchingId: Int
    ): GetChatRoomInfoDto?




}