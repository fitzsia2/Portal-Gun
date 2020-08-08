package com.example.portalgun.ui.main.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.portalgun.repo.IRickAndMortyRepository
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    rickAndMortyRepository: IRickAndMortyRepository
) : ViewModel() {

    val characterStream = rickAndMortyRepository.getPagedCharacterStream().cachedIn(viewModelScope)
}
