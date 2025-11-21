package com.moa.app.network.adapter

import com.moa.app.network.model.BaseResponse
import com.moa.app.network.model.NetworkResult
import retrofit2.Response

internal class UnitResponseHandler : ResponseHandler<Unit>() {
    override fun handle(response: Response<BaseResponse<Unit>>): NetworkResult<Unit> {
        val body = response.body()

        return if (response.isSuccessful && body != null) {
            if (body.success) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(code = response.code(), message = body.message)
            }
        } else {
            NetworkResult.Error(code = response.code(), message = response.message())
        }
    }
}
