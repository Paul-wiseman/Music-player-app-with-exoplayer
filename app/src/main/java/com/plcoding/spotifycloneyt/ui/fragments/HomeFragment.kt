package com.plcoding.spotifycloneyt.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.plcoding.spotifycloneyt.R
import com.plcoding.spotifycloneyt.adapters.SongAdapter
import com.plcoding.spotifycloneyt.data.entities.Song
import com.plcoding.spotifycloneyt.data.local.entities.SongEntity
import com.plcoding.spotifycloneyt.databinding.FragmentHomeBinding
import com.plcoding.spotifycloneyt.other.Status
import com.plcoding.spotifycloneyt.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var listOfSongs = emptyList<Song>()


    @Inject
    lateinit var songAdapter: SongAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupRecyclerView()
        subscribeToObservers()
        setUpSearchView()

        songAdapter.setItemClickListener {
            mainViewModel.saveSong(SongEntity(it))
            mainViewModel.playOrToggleSong(it)
            findNavController().navigate(
                R.id.globalActionToSongFragment
            )
        }

        binding.radioGroup.setOnCheckedChangeListener { _, radioButtonID ->
            when (radioButtonID) {
                R.id.leftRadioButton -> {
                    val shuffledList = listOfSongs.shuffled()
                    songAdapter.songs = shuffledList
                }

                R.id.rightRadioButton -> {
                    mainViewModel.readSongs.observe(viewLifecycleOwner){ listOfSongsEntity ->
                        songAdapter.songs = listOfSongsEntity.map { songEntity ->
                            songEntity.song
                        }.distinct()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() = binding.rvAllSongs.apply {
        adapter = songAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeToObservers() {
        mainViewModel.mediaItems.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    binding.allSongsProgressBar.isVisible = false
                    result.data?.let { songs ->
                        listOfSongs = songs
                        Log.d("AdapterSongs", "---songs--  $songs")
                        songAdapter.songs = songs
                    }
                }
                Status.ERROR -> Unit
                Status.LOADING -> binding.allSongsProgressBar.isVisible = true
            }
        }
    }

    private fun setUpSearchView() {
        binding.searchView.editText?.doOnTextChanged { text, _, _, _ ->
            val filterList = ArrayList<Song>()
            if (listOfSongs.isNotEmpty()) {
                listOfSongs.forEach {
                    if (text != null) {
                        if (it.subtitle.toLowerCase()
                                .contains(text.toString().toLowerCase(), true) || it.artistName.toLowerCase()
                                .contains(text.toString().toLowerCase(), true) || it.title.toLowerCase()
                                .contains(text.toString().toLowerCase(), true)
                        ) {
                            filterList.add(it)
                        }

                    }
                }
            }
            songAdapter.songs = filterList
            songAdapter.notifyDataSetChanged()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
















