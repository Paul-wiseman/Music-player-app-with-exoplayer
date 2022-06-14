package com.plcoding.spotifycloneyt.data.remote

import android.content.Context
import com.example.techinnoverassessment.data.entities.ListOfSongs
import com.plcoding.spotifycloneyt.data.entities.ListOfSongsItem
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.plcoding.spotifycloneyt.R
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class MusicDatabase @Inject constructor(context: Context) {
    val gson: Gson = GsonBuilder().create()
    val json: InputStream = context.resources.openRawResource(R.raw.songs)


    fun getAllSongs(): List<ListOfSongsItem> {
        return try {
            val listOfSongs = mutableListOf<ListOfSongsItem>()
            val reader = BufferedReader(InputStreamReader(json))
            val listFromJson: ListOfSongs = gson.fromJson(reader, ListOfSongs::class.java)
            listOfSongs.addAll(listFromJson)
            listOfSongs
        } catch(e: Exception) {
            emptyList()
        }
    }
}