package com.moa.app.network.adapter

import com.moa.app.network.model.BaseResponse
import com.moa.app.network.model.NetworkResult
import retrofit2.Response

internal open class ResponseHandler<T> {
    open fun handle(response: Response<BaseResponse<T>>): NetworkResult<T> {
        val body = response.body()

        if (response.isSuccessful) {
            return if (body != null && body.success) {
                @Suppress("UNCHECKED_CAST")
                NetworkResult.Success(body.data as T)
            } else {
                NetworkResult.Error(code = response.code(), message = body?.message ?: "Unknown Error")
            }
        }

        return NetworkResult.Error(code = response.code(), message = response.message())
    }
}
