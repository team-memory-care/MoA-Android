package com.moa.app.network.auth

import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val invocation = originalRequest.tag(Invocation::class.java)
        val hasNoAuthAnnotation = invocation?.method()?.getAnnotation(NoAuth::class.java) != null

        if (hasNoAuthAnnotation) return chain.proceed(originalRequest)

        val accessToken = runBlocking {
            tokenManager.getAccessToken()
        }

        if (accessToken.isNullOrBlank()) return chain.proceed(originalRequest)

        val newRequest = originalRequest
            .newBuilder()
            .addHeader(name = AUTH_HEADER, value = "$TOKEN_TYPE $accessToken")
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        const val AUTH_HEADER = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }
}
