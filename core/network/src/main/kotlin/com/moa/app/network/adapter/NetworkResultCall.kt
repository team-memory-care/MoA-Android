package com.moa.app.network.adapter

import com.moa.app.network.model.BaseResponse
import com.moa.app.network.model.NetworkResult
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class NetworkResultCall<T>(
    private val proxy: Call<BaseResponse<T>>,
    private val responseHandler: ResponseHandler<T> = ResponseHandler(),
) : Call<NetworkResult<T>> {

    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        proxy.enqueue(
            object : Callback<BaseResponse<T>> {
                override fun onResponse(
                    call: Call<BaseResponse<T>>,
                    response: Response<BaseResponse<T>>,
                ) {
                    val networkResult = responseHandler.handle(response)
                    callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
                }

                override fun onFailure(call: Call<BaseResponse<T>>, t: Throwable) {
                    val networkResult = NetworkResult.Exception(t)
                    callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
                }
            },
        )
    }

    override fun clone(): Call<NetworkResult<T>> = NetworkResultCall(proxy.clone(), responseHandler)
    override fun execute(): Response<NetworkResult<T>> =
        throw UnsupportedOperationException("NetworkResultCall doesn't support execute")

    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun cancel() = proxy.cancel()
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
}
