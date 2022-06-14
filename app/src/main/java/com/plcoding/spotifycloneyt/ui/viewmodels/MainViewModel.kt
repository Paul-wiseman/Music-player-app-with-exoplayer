package com.plcoding.spotifycloneyt.ui.viewmodels

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.plcoding.spotifycloneyt.data.entities.Song
import com.plcoding.spotifycloneyt.data.local.LocalDataSource
import com.plcoding.spotifycloneyt.data.local.entities.SongEntity
import com.plcoding.spotifycloneyt.exoplayer.MusicServiceConnection
import com.plcoding.spotifycloneyt.exoplayer.isPlayEnabled
import com.plcoding.spotifycloneyt.exoplayer.isPlaying
import com.plcoding.spotifycloneyt.exoplayer.isPrepared
import com.plcoding.spotifycloneyt.other.Constants.MEDIA_ROOT_ID
import com.plcoding.spotifycloneyt.other.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val musicServiceConnection: MusicServiceConnection,
    private val localDataSource: LocalDataSource
) : ViewModel() {
    private val _mediaItems = MutableLiveData<Resource<List<Song>>>()
    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems

    /**
     * from Room Database*/
    val readSongs: LiveData<List<SongEntity>> = localDataSource.readSongs().asLiveData()

    val isConnected = musicServiceConnection.isConnected
    val networkError = musicServiceConnection.networkError
    val curPlayingSong = musicServiceConnection.curPlayingSong
    val playbackState = musicServiceConnection.playbackState

    init {
        _mediaItems.postValue(Resource.loading(null))
        musicServiceConnection.subscribe(
            MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {
                override fun onChildrenLoaded(
                    parentId: String,
                    children: MutableList<MediaBrowserCompat.MediaItem>
                ) {
                    super.onChildrenLoaded(parentId, children)
                    val items = children.map {
                        Log.d("mmmmm", "subtitle  ${it.description.subtitle} ")
                        Log.d("mmmmm", "title  ${it.description.title} ")
                        Log.d("mmmmm", "description  ${it.description.description} ")
                        Log.d("mmmmm", "mediaDescription  ${it.description.mediaDescription} ")
                        Song(
                            it.mediaId!!,
                            it.description.title.toString(),
                            it.description.subtitle.toString().split("&")[1],
                            it.description.mediaUri.toString(),
                            it.description.iconUri.toString(),
                            it.description.subtitle.toString().split("&")[0]
                        )
                    }
                    _mediaItems.postValue(Resource.success(items))
                }
            })
    }


    fun saveSong(songEntity: SongEntity) = viewModelScope.launch(Dispatchers.IO) {
        localDataSource.insertSong(songEntity)
    }


    fun skipToNextSong() {
        musicServiceConnection.transportControls.skipToNext()
    }

    fun skipToPreviousSong() {
        musicServiceConnection.transportControls.skipToPrevious()
    }

    fun seekTo(pos: Long) {
        musicServiceConnection.transportControls.seekTo(pos)
    }

    fun playOrToggleSong(mediaItem: Song, toggle: Boolean = false) {
        val isPrepared = playbackState.value?.isPrepared ?: false
        if (isPrepared && mediaItem.mediaId ==
            curPlayingSong.value?.getString(METADATA_KEY_MEDIA_ID)
        ) {
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> if (toggle) musicServiceConnection.transportControls.pause()
                    playbackState.isPlayEnabled -> musicServiceConnection.transportControls.play()
                    else -> Unit
                }
            }
        } else {
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.mediaId, null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.unsubscribe(
            MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}

















