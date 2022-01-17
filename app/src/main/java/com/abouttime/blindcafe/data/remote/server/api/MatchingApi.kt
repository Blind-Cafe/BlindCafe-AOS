package com.abouttime.blindcafe.data.remote.server.api

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.Retrofit.DELETE_DISMISS_MATCHING_URL
import com.abouttime.blindcafe.common.constants.Retrofit.DELETE_EXIT_CHAT_ROOM_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_CHAT_ROOMS_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_CHAT_ROOM_INFO_URL
import com.abouttime.blindcafe.common.constants.Retrofit.GET_TOPIC_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_ACCEPT_MATCHING_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_CANCEL_MATCHING_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_DRINK_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_ENTERING_LOG_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_MATCHING_REQUEST_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_MESSAGE_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_REPORT_URL
import com.abouttime.blindcafe.data.remote.server.dto.matching.*
import com.abouttime.blindcafe.data.remote.server.dto.matching.accept.PostAcceptMatchingDto
import com.abouttime.blindcafe.data.remote.server.dto.matching.report.PostReportDto
import com.abouttime.blindcafe.data.remote.server.dto.matching.send.PostMessageDto
import com.abouttime.blindcafe.data.remote.server.dto.matching.topic.GetTopicDto
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

    @POST(POST_ACCEPT_MATCHING_URL)
    suspend fun postAcceptMatching(
        @Path("matchingId") matchingId: Int
    ): PostAcceptMatchingDto?

    @DELETE(DELETE_DISMISS_MATCHING_URL) // 바디 없음 주의
    suspend fun deleteDismissMatching(
        @Path("matchingId") matchingId: Int,
        @Query("reason") reason: Int
    )

    @POST(POST_ENTERING_LOG_URL)
    suspend fun postEnteringLog(
        @Path("matchingId") matchingId: Int
    )

    @POST(POST_MESSAGE_URL)
    suspend fun postMessage(
        @Path("matchingId") matchingId: Int,
        @Body postMessageDto: PostMessageDto
    )


}