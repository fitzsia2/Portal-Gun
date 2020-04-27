package com.example.portalgun.ui.main.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.portalgun.remote.rickandmorty.Character
import com.example.portalgun.repo.IRickAndMortyRepository
import com.example.portalgun.util.launchWithStatus
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val rickAndMortyRepository: IRickAndMortyRepository
) : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        launchWithStatus(_loading) {
            _characters.value = rickAndMortyRepository.characters()
        }
    }
}
