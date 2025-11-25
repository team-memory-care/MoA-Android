package com.moa.app.data.di

import com.moa.app.data.auth.datasource.AuthDataSource
import com.moa.app.data.auth.datasourceImpl.AuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthDataSource(impl: AuthDataSourceImpl): AuthDataSource

}
