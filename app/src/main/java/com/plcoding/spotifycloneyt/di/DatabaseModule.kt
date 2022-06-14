package com.plcoding.spotifycloneyt.di

import android.content.Context
import androidx.room.Room
import com.plcoding.spotifycloneyt.data.local.LocalDataSource
import com.plcoding.spotifycloneyt.data.local.SongDao
import com.plcoding.spotifycloneyt.data.local.SongsDatabase
import com.plcoding.spotifycloneyt.other.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        SongsDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: SongsDatabase) = database.songsDao()

    @Singleton
    @Provides
    fun provideLocalDataSource(songDao: SongDao): LocalDataSource = LocalDataSource(songDao)

}