package com.moa.app.di

import com.moa.app.di.adapter.TokenManagerAdapter
import com.moa.app.network.auth.TokenManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenModule {

    @Binds
    @Singleton
    abstract fun bindTokenManager(adapter: TokenManagerAdapter): TokenManager
}
