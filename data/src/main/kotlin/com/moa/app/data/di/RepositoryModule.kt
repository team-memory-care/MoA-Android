package com.moa.app.data.di

import com.moa.app.data.auth.repositoryImpl.AuthRepositoryImpl
import com.moa.app.data.user.repositoryImpl.UserRepositoryImpl
import com.moa.app.data.quiz.repositoryImpl.QuizRepositoryImpl
import com.moa.app.domain.auth.repository.AuthRepository
import com.moa.app.domain.user.repository.UserRepository
import com.moa.app.domain.quiz.repository.QuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindQuizRepository(impl: QuizRepositoryImpl): QuizRepository

}
