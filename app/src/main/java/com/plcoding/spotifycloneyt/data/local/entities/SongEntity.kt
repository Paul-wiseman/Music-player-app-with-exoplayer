package com.plcoding.spotifycloneyt.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.plcoding.spotifycloneyt.data.entities.Song
import com.plcoding.spotifycloneyt.other.Constants.SONG_TABLE

@Entity(tableName = SONG_TABLE)
class SongEntity(
    @Embedded
    var song: Song
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}