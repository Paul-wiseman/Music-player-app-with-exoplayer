package com.plcoding.spotifycloneyt.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plcoding.spotifycloneyt.data.local.entities.SongEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(songEntity: SongEntity)

    @Query("SELECT * FROM song_table ORDER BY id ASC")
    fun readFoodJoke(): Flow<List<SongEntity>>

}