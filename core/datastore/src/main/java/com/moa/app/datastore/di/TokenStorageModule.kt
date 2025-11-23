package com.moa.app.datastore.di

import com.moa.app.datastore.token.DataStoreTokenStorage
import com.moa.app.datastore.token.TokenStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenStorageModule {

    @Binds
    @Singleton
    abstract fun bindTokenStorage(impl: DataStoreTokenStorage): TokenStorage
}
