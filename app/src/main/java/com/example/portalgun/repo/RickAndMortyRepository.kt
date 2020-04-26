package com.example.portalgun.repo

import com.example.portalgun.remote.rickandmorty.Character
import com.example.portalgun.remote.rickandmorty.RickAndMortyService
import javax.inject.Inject

interface IRickAndMortyRepository {

    suspend fun characters(): List<Character>
}

class RickAndMortyRepository @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) : IRickAndMortyRepository {

    override suspend fun characters() = rickAndMortyService.characters().characters
}
