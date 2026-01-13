package com.moa.app.feature.senior.quiz.stt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SttModule {

    @Binds
    @Singleton
    abstract fun bindSttManager(sttManagerImpl: AndroidSttManager): SttManager
}
