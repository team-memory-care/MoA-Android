package com.moa.app.data.di

import com.moa.app.data.auth.service.AuthService
import com.moa.app.data.user.service.UserService
import com.moa.app.data.quiz.service.QuizService
import com.moa.app.data.report.service.ReportService
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

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideQuizService(retrofit: Retrofit): QuizService =
        retrofit.create(QuizService::class.java)

    @Provides
    @Singleton
    fun provideReportService(retrofit: Retrofit): ReportService =
        retrofit.create(ReportService::class.java)
}
