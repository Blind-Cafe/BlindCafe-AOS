package com.abouttime.blindcafe.data.server.dto.user_info

data class DeleteAccountResponse(
    val code: String,
    val message: String,
    val nickname: String
)