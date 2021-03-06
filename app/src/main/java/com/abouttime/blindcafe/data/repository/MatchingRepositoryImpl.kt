package com.abouttime.blindcafe.data.repository

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.data.server.api.MatchingApi
import com.abouttime.blindcafe.data.server.dto.matching.*
import com.abouttime.blindcafe.data.server.dto.matching.accept.PostAcceptMatchingDto
import com.abouttime.blindcafe.data.server.dto.matching.report.PostReportDto
import com.abouttime.blindcafe.data.server.dto.matching.send.PostMessageDto
import com.abouttime.blindcafe.data.server.dto.matching.topic.GetTopicDto
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

    override suspend fun exitChatRoom(matchingId: Int, reason: Int): BaseResponse? {
        return matchingApi.exitChatRoom(matchingId, reason)
    }

    override suspend fun getTopic(matchingId: Int): GetTopicDto? {
        return matchingApi.getTopic(matchingId)
    }

    override suspend fun postReport(report: PostReportDto): BaseResponse? {
        return matchingApi.postReport(report)
    }

    override suspend fun postCancelMatching(): BaseResponse? {
        return matchingApi.postCancelMatching()
    }

    override suspend fun postAcceptMatching(matchingId: Int): PostAcceptMatchingDto? {
        return matchingApi.postAcceptMatching(matchingId)
    }

    override suspend fun deleteDismissMatching(matchingId: Int, reason: Int) {
        return matchingApi.deleteDismissMatching(matchingId = matchingId, reason = reason)
    }

    override suspend fun postEnteringLog(matchingId: Int) {
        matchingApi.postEnteringLog(matchingId)
    }


}