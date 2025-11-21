package com.moa.app.network.adapter

import com.moa.app.network.model.NetworkResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null

        val callType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (getRawType(callType) != NetworkResult::class.java) return null

        val successType = getParameterUpperBound(0, callType as ParameterizedType)

        return NetworkResultCallAdapter(successType)
    }

    companion object {
        fun create() = NetworkResultCallAdapterFactory()
    }
}
