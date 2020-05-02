package com.example.portalgun.ui.main.detail.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.portalgun.remote.rickandmorty.Episode
import com.example.portalgun.repo.IRickAndMortyRepository
import com.example.portalgun.util.launchWithStatus
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val rickAndMortyRepository: IRickAndMortyRepository
) : ViewModel() {

    private val _episodes = MutableLiveData<List<Episode>>()
    val episodes: LiveData<List<Episode>> = _episodes
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun loadEpisodes(ids: List<Int>) {
        launchWithStatus(_loading, _error) {
            _episodes.value = rickAndMortyRepository.episodes(ids)
        }
    }
}
