package com.abouttime.blindcafe.domain.repository

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.data.server.dto.matching.*
import com.abouttime.blindcafe.data.server.dto.matching.accept.PostAcceptMatchingDto
import com.abouttime.blindcafe.data.server.dto.matching.report.PostReportDto
import com.abouttime.blindcafe.data.server.dto.matching.send.PostMessageDto
import com.abouttime.blindcafe.data.server.dto.matching.topic.GetTopicDto

interface MatchingRepository {

    suspend fun postMatchingRequest(): PostMatchingRequestResponse?
    suspend fun postDrink(matchingId: Int, drink: PostDrinkDto): PostDrinkResponse?
    suspend fun getChatRooms(): GetChatRoomsDto?
    suspend fun getChatRoomInfo(matchingId: Int): GetChatRoomInfoDto?
    suspend fun exitChatRoom(matchingId: Int, reason: Int): BaseResponse?
    suspend fun getTopic(matchingId: Int): GetTopicDto?
    suspend fun postReport(report: PostReportDto): BaseResponse?
    suspend fun postCancelMatching(): BaseResponse?
    suspend fun postAcceptMatching(matchingId: Int): PostAcceptMatchingDto?
    suspend fun deleteDismissMatching(matchingId: Int, reason: Int)
    suspend fun postEnteringLog(matchingId: Int)
}