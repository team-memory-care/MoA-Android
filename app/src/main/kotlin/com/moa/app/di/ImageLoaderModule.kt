package com.moa.app.di

import android.content.Context
import coil3.EventListener
import coil3.ImageLoader
import com.moa.app.feature.senior.quiz.internal.ImageLoadingLatencyEventListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {

    @Provides
    @Singleton
    fun provideImageEventListenerFactory(): EventListener.Factory =
        ImageLoadingLatencyEventListener.Factory

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        eventListenerFactory: EventListener.Factory,
    ): ImageLoader = ImageLoader.Builder(context)
        .eventListenerFactory(eventListenerFactory)
        .build()
}
