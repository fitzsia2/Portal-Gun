package com.example.portalgun.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portalgun.remote.rickandmorty.Character
import com.example.portalgun.repo.IRickAndMortyRepository
import kotlinx.coroutines.launch
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
        launchWithStatus {
            _characters.value = rickAndMortyRepository.characters()
        }
    }

    private fun launchWithStatus(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                _loading.value = true
                block()
            } catch (e: Exception) {
            } finally {
                _loading.value = false
            }
        }
    }
}
