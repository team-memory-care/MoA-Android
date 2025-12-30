package com.moa.app.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.moa.app.datastore.token.encryption.CryptoManager
import com.moa.app.datastore.token.encryption.EncryptedTokenSerializer
import com.moa.app.datastore.token.model.TokenData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideCryptoManager(): CryptoManager = CryptoManager()

    @Provides
    @Singleton
    fun provideTokenDataStore(
        @ApplicationContext context: Context,
        cryptoManager: CryptoManager,
    ): DataStore<TokenData> {
        return DataStoreFactory.create(
            serializer = EncryptedTokenSerializer(cryptoManager),
            produceFile = {
                context.dataStoreFile("auth_token_data.enc")
            },
            corruptionHandler = ReplaceFileCorruptionHandler { exception ->
                Timber.tag("TokenDataStore")
                    .e(exception, "Token data corrupted, replacing with empty data")

                TokenData.INIT
            },
        )
    }
}
