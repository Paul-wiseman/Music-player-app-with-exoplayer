package com.plcoding.spotifycloneyt.exoplayer

import android.support.v4.media.MediaMetadataCompat
import android.util.Log
import com.plcoding.spotifycloneyt.data.entities.Song



fun MediaMetadataCompat.toSong(): Song? {
    return description?.let {

        Log.d("ListOfSongsItem", "toSong: description ----${it.description} ")
        Log.d("ListOfSongsItem", "toSong: iconUri ----${it.iconUri} ")
        Log.d("ListOfSongsItem", "toSong: subtitle----${it.subtitle} ")
        Log.d("ListOfSongsItem", "toSong: title----${it.title} ")
        Log.d("ListOfSongsItem", "toSong: iconBitmap----${it.iconBitmap} ")
        Log.d("ListOfSongsItem", "toSong: mediaUri ----${it.mediaUri} ")
        Log.d("ListOfSongsItem", "toSong: mediaDescription ----${it.mediaDescription} ")
        Song(
            it.mediaId ?: "",
            it.title.toString(),
            it.subtitle.toString().split("&")[1],
            it.mediaUri.toString(),
            it.iconUri.toString(),
            it.subtitle.toString().split("&")[0]
        )
    }
}