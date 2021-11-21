package com.abouttime.blindcafe.data.server.api

import com.abouttime.blindcafe.common.constants.Retrofit.POST_DRINK_URL
import com.abouttime.blindcafe.common.constants.Retrofit.POST_MATCHING_REQUEST_URL
import com.abouttime.blindcafe.data.server.dto.matching.PostDrinkDto
import com.abouttime.blindcafe.data.server.dto.matching.PostDrinkResponse
import com.abouttime.blindcafe.data.server.dto.matching.PostMatchingRequestResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MatchingApi {
    @POST(POST_MATCHING_REQUEST_URL)
    suspend fun postMatchingRequest(): PostMatchingRequestResponse?

    @POST(POST_DRINK_URL)
    suspend fun postDrink(
        @Path("matchingId") matchingId: Int,
        @Body drink: PostDrinkDto
    ): PostDrinkResponse?


}