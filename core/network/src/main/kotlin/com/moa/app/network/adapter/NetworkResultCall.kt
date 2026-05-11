package com.moa.app.network.adapter

import com.moa.app.network.model.BaseResponse
import com.moa.app.network.model.NetworkResult
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

class NetworkResultCall<T>(
    private val delegate: Call<BaseResponse<T>>,
    private val isUnit: Boolean,
    private val errorBodyConverter: Converter<ResponseBody, BaseResponse<Unit>>,
) : Call<NetworkResult<T>> {

    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        delegate.enqueue(
            object : Callback<BaseResponse<T>> {

                override fun onResponse(
                    call: Call<BaseResponse<T>>,
                    response: Response<BaseResponse<T>>,
                ) {
                    val result = if (response.isSuccessful) {
                        handleSuccess(response)
                    } else {
                        handleHttpError(response)
                    }
                    callback.onResponse(this@NetworkResultCall, Response.success(result))
                }

                override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
                    callback.onResponse(
                        this@NetworkResultCall,
                        Response.success(NetworkResult.Exception(t)),
                    )
                }
            },
        )
    }

    private fun handleSuccess(response: Response<BaseResponse<T>>): NetworkResult<T> {
        val body = response.body()
            ?: return NetworkResult.Error(response.code(), "Empty body")

        if (!body.success) {
            return NetworkResult.Error(response.code(), body.message)
        }

        if (isUnit) {
            @Suppress("UNCHECKED_CAST")
            return NetworkResult.Success(Unit as T)
        }

        return body.data
            ?.let { NetworkResult.Success(it) }
            ?: NetworkResult.Error(response.code(), body.message)
    }

    private fun handleHttpError(response: Response<BaseResponse<T>>): NetworkResult<T> {
        val errorBody = response.errorBody()
            ?.let { parseErrorBody(it) }

        return NetworkResult.Error(
            code = response.code(),
            message = errorBody?.message ?: response.message(),
        )
    }

    private fun parseErrorBody(errorBody: ResponseBody): BaseResponse<Unit>? =
        try {
            errorBodyConverter.convert(errorBody)
        } catch (e: Exception) {
            null
        }

    override fun clone(): Call<NetworkResult<T>> =
        NetworkResultCall(delegate.clone(), isUnit, errorBodyConverter)

    override fun execute(): Response<NetworkResult<T>> =
        throw UnsupportedOperationException("NetworkResultCall doesn't support execute")

    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun cancel() = delegate.cancel()
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()
}
