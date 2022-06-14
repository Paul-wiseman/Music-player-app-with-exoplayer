package com.plcoding.spotifycloneyt.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foody.data.database.RecipesTypeConverter
import com.plcoding.spotifycloneyt.data.local.entities.SongEntity

@Database(
    entities = [SongEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class SongsDatabase: RoomDatabase() {

    abstract fun songsDao(): SongDao

}