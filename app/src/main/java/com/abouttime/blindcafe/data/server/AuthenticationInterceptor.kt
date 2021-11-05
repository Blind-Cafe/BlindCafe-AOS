package com.abouttime.blindcafe.data.server

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = null
        if (jwtToken != null) {
            builder.addHeader("nego with server", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}