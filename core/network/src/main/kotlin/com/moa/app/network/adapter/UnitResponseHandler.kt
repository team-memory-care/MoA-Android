package com.moa.app.network.adapter

import com.moa.app.network.model.BaseResponse
import com.moa.app.network.model.NetworkResult
import retrofit2.Response

internal class UnitResponseHandler : ResponseHandler<Unit>() {
    override fun handle(response: Response<BaseResponse<Unit>>): NetworkResult<Unit> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null && !body.success) {
                NetworkResult.Error(code = response.code(), message = body.message)
            } else {
                NetworkResult.Success(Unit)
            }
        } else {
            NetworkResult.Error(code = response.code(), message = response.message())
        }
    }
}
