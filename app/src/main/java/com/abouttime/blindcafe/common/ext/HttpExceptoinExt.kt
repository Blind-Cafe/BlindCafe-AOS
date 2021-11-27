package com.abouttime.blindcafe.common.ext

import android.util.Log
import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.LogTag
import org.json.JSONObject
import retrofit2.HttpException

internal fun HttpException.parseErrorBody(): BaseResponse {
    this.response()?.errorBody()?.string()?.let {
        val errorBody = JSONObject(it)
        val code = errorBody.getString("code")
        val message = errorBody.getString("message")
        Log.e(LogTag.RETROFIT_TAG, errorBody.toString())
        Log.e(LogTag.RETROFIT_TAG, code)
        Log.e(LogTag.RETROFIT_TAG, message)
        return BaseResponse(code = code, message = message)
    } ?: kotlin.run {
        return BaseResponse(code = "-1", message = "알 수 없는 에러")
    }
}
