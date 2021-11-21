package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.constants.Retrofit.GET_INTEREST_URL
import com.abouttime.blindcafe.data.server.dto.interest.GetInterestResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface InterestApi {
    @GET(GET_INTEREST_URL)
    suspend fun getInterests(
        @Query("id") id1: Int,
        @Query("id") id2: Int,
        @Query("id") id3: Int
    ): GetInterestResponse?
}