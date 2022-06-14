package com.plcoding.spotifycloneyt.data.local

import com.plcoding.spotifycloneyt.data.local.entities.SongEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val songDao: SongDao
) {

    suspend fun insertSong(songEntity: SongEntity) {
        songDao.insertSong(songEntity)
    }

    fun readSongs(): Flow<List<SongEntity>> {
        return songDao.readFoodJoke()
    }
}