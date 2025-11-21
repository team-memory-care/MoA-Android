package com.moa.app.network.adapter

import com.moa.app.network.model.BaseResponse
import com.moa.app.network.model.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResultCallAdapter(
    private val successType: Type
) : CallAdapter<BaseResponse<*>, Call<NetworkResult<*>>> {

    override fun responseType(): Type = ParameterizedTypeImpl(BaseResponse::class.java, successType)

    @Suppress("UNCHECKED_CAST")
    override fun adapt(call: Call<BaseResponse<*>>): Call<NetworkResult<*>> {
        return NetworkResultCall(call as Call<BaseResponse<Any>>) as Call<NetworkResult<*>>
    }
}
