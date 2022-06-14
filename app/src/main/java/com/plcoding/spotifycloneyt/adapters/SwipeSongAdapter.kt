package com.plcoding.spotifycloneyt.adapters

import android.util.Log
import androidx.recyclerview.widget.AsyncListDiffer
import com.google.android.material.textview.MaterialTextView
import com.plcoding.spotifycloneyt.R

class SwipeSongAdapter : BaseSongAdapter(R.layout.swipe_item) {

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.itemView.apply {
            Log.d("AdapterSong", " subtitle --- ${song.subtitle}")
            Log.d("AdapterSong", " artistName --- ${song.artistName}")
//            val text = "${song.subtitle} - ${song.artistName}"

            holder.itemView.findViewById<MaterialTextView>(R.id.song_title).text = song.subtitle
            holder.itemView.findViewById<MaterialTextView>(R.id.artist_name).text = song.artistName

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(song)
                }
            }
        }
    }

}



















