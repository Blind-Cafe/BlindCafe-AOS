package com.abouttime.blindcafe.data.remote.server.dto.user_info

data class DeleteAccountResponse(
    val code: String,
    val message: String,
    val nickname: String
)