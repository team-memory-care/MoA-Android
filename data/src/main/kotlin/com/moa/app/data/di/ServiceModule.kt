package com.moa.app.data.di

import com.moa.app.data.auth.service.AuthService
import com.moa.app.network.auth.TokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideReissueTokenService(retrofit: Retrofit): TokenService =
        retrofit.create(TokenService::class.java)
}
