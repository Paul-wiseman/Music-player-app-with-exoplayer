package com.plcoding.spotifycloneyt.adapters


import androidx.recyclerview.widget.AsyncListDiffer
import com.bumptech.glide.RequestManager
import com.google.android.material.textview.MaterialTextView
import com.plcoding.spotifycloneyt.R
import javax.inject.Inject

class SongAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseSongAdapter(R.layout.list_item) {

    override val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.itemView.apply {
            this.findViewById<MaterialTextView>(R.id.tvPrimary).text =  song.title
            this.findViewById<MaterialTextView>(R.id.tvSecondary).text = song.subtitle
            glide.load(song.imageUrl).into(this.findViewById(R.id.ivItemImage))
            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(song)
                }
            }
        }
    }

}



















