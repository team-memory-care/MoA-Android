package com.moa.app.network.di

import android.content.Context
import com.moa.app.network.BuildConfig
import com.moa.app.network.adapter.NetworkResultCallAdapterFactory
import com.moa.app.network.auth.AuthInterceptor
import com.moa.app.network.auth.TokenManager
import com.moa.app.network.mock.auth.AuthMockInterceptor
import com.moa.app.network.mock.quiz.QuizMockInterceptor
import com.moa.app.network.mock.user.UserMockInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val APPLICATION_JSON = "application/json"

    @Provides
    @Singleton
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory(APPLICATION_JSON.toMediaType())

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Provides
    @Singleton
    fun provideAuthMockInterceptor(
        @ApplicationContext context: Context,
    ): AuthMockInterceptor = AuthMockInterceptor(context)

    @Provides
    @Singleton
    fun provideUserMockInterceptor(
        @ApplicationContext context: Context,
    ): UserMockInterceptor = UserMockInterceptor(context)

    @Provides
    @Singleton
    fun provideQuizMockInterceptor(
        @ApplicationContext context: Context,
    ): QuizMockInterceptor = QuizMockInterceptor(context)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        authMockInterceptor: AuthMockInterceptor,
        userMockInterceptor: UserMockInterceptor,
        quizMockInterceptor: QuizMockInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(authInterceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(authMockInterceptor)
                addInterceptor(userMockInterceptor)
                addInterceptor(quizMockInterceptor)
            }
        }
        .connectTimeout(10L, TimeUnit.SECONDS)
        .writeTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addCallAdapterFactory(NetworkResultCallAdapterFactory())
        .addConverterFactory(converterFactory)
        .client(okHttpClient)
        .build()
}
