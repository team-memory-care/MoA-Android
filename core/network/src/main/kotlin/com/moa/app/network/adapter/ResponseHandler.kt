package com.moa.app.network.adapter

import com.moa.app.network.model.BaseResponse
import com.moa.app.network.model.NetworkResult
import retrofit2.Response

internal open class ResponseHandler<T> {
    open fun handle(response: Response<BaseResponse<T>>): NetworkResult<T> {
        val body = response.body()

        return if (response.isSuccessful && body != null) {
            if (body.success) {
                body.data?.let {
                    NetworkResult.Success(it)
                } ?: NetworkResult.Error(code = response.code(), message = "Response data is null")
            } else {
                NetworkResult.Error(code = response.code(), message = body.message)
            }
        } else {
            NetworkResult.Error(code = response.code(), message = response.message())
        }
    }
}
