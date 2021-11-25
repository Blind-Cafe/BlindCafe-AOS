package com.abouttime.blindcafe.common.ext

import android.util.Log
import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.LogTag
import org.json.JSONObject
import retrofit2.HttpException

internal fun HttpException.parseErrorBody(): BaseResponse {
    val errorBody = JSONObject(this.response()?.errorBody()?.string())
    val code = errorBody.getString("code")
    val message = errorBody.getString("message")
    Log.e(LogTag.RETROFIT_TAG, errorBody.toString())
    Log.e(LogTag.RETROFIT_TAG, code)
    Log.e(LogTag.RETROFIT_TAG, message)

    return BaseResponse(code = code, message = message)

}
