package com.moa.app.network.adapter

import com.moa.app.network.model.BaseResponse
import com.moa.app.network.model.NetworkResult
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResultCallAdapter<T>(
    private val responseType: Type,
    private val isUnit: Boolean,
    private val errorBodyConverter: Converter<ResponseBody, BaseResponse<Unit>>,
) : CallAdapter<BaseResponse<T>, Call<NetworkResult<T>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<BaseResponse<T>>): Call<NetworkResult<T>> =
        NetworkResultCall(call, isUnit, errorBodyConverter)
}

class NetworkResultCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be parameterized" }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != NetworkResult::class.java) return null
        check(responseType is ParameterizedType) { "NetworkResult must be parameterized" }

        val successType = getParameterUpperBound(0, responseType)
        val isUnit = getRawType(successType) == Unit::class.java

        val baseResponseType = object : ParameterizedType {
            override fun getRawType(): Type = BaseResponse::class.java
            override fun getOwnerType(): Type? = null
            override fun getActualTypeArguments(): Array<Type> = arrayOf(successType)
        }

        val errorBodyConverter: Converter<ResponseBody, BaseResponse<Unit>> =
            retrofit.responseBodyConverter(
                object : ParameterizedType {
                    override fun getRawType(): Type = BaseResponse::class.java
                    override fun getOwnerType(): Type? = null
                    override fun getActualTypeArguments(): Array<Type> = arrayOf(Unit::class.java)
                },
                annotations,
            )

        return NetworkResultCallAdapter<Any>(baseResponseType, isUnit, errorBodyConverter)
    }
}
