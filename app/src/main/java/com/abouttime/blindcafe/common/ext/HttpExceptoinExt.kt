package com.abouttime.blindcafe.common.ext

import android.util.Log
import com.abouttime.blindcafe.common.base.BaseResponse
import com.abouttime.blindcafe.common.constants.LogTag
import com.abouttime.blindcafe.common.constants.LogTag.ERROR_TAG
import com.abouttime.blindcafe.presentation.GlobalLiveData
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

internal fun HttpException.parseErrorBody(): BaseResponse {
    this.response()?.errorBody()?.string()?.let {
        return try {
            val errorBody = JSONObject(it)
            val code = errorBody.getString("code")
            val message = errorBody.getString("message")
            Log.e(ERROR_TAG, errorBody.toString())
            Log.e(ERROR_TAG, code)
            Log.e(ERROR_TAG, message)
            if (code == "1007") {
                GlobalLiveData.suspendUserEvent.postValue(true)
            }

            BaseResponse(code = code, message = message)
        } catch (e: Exception) {
            Log.e(ERROR_TAG, e.toString())
            BaseResponse(code = "-1", message = "$e")
        }
    } ?: kotlin.run {
        return BaseResponse(code = "-1", message = "알 수 없는 에러")
    }
}
