package com.example.foody.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.plcoding.spotifycloneyt.data.entities.Song

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun songToString(song: Song): String {
        return gson.toJson(song)
    }

    @TypeConverter
    fun stringToSong(data: String): Song {
        val listType = object : TypeToken<Song>() {}.type
        return gson.fromJson(data, listType)
    }

}