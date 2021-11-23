package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.Retrofit.DELETE_EXIT_CHAT_ROOM_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_CHAT_ROOMS_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_CHAT_ROOM_INFO_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_TOPIC_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_CANCEL_MATCHING_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_DRINK_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_MATCHING_REQUEST_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_REPORT_URL
import com.abouttime.blindcafe.data.server.dto.matching.*
import com.abouttime.blindcafe.data.server.dto.matching.report.PostReportDto
import com.abouttime.blindcafe.data.server.dto.matching.topic.GetTopicDto
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


    @DELETE(DELETE_EXIT_CHAT_ROOM_URL)
    suspend fun exitChatRoom(
        @Path("matchingId") matchingId: Int,
        @Query("reason") reason: Int
    ): BaseResponse?

    @GET(GET_TOPIC_URL)
    suspend fun getTopic(
        @Path("matchingId") matchingId: Int
    ): GetTopicDto?

    @POST(POST_REPORT_URL)
    suspend fun postReport(
        @Body report: PostReportDto
    ): BaseResponse?

    @POST(POST_CANCEL_MATCHING_URL)
    suspend fun postCancelMatching(): BaseResponse?




}