package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.Retrofit.GET_INTEREST_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_INTEREST_URL
import com.abouttime.blindcafe.data.server.dto.interest.GetInterestResponse
import com.abouttime.blindcafe.data.server.dto.interest.PostInterestDto
import retrofit2.http.*

interface InterestApi {
    @GET(GET_INTEREST_URL)
    suspend fun getInterests(
        @Query("id") id1: Int,
        @Query("id") id2: Int,
        @Query("id") id3: Int
    ): GetInterestResponse?

    @PUT(POST_INTEREST_URL)
    suspend fun postInterests(
        @Body postInterestDto: PostInterestDto
    ): BaseResponse?
}