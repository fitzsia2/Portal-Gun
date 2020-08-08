package com.example.portalgun.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.portalgun.remote.rickandmorty.Character
import com.example.portalgun.remote.rickandmorty.Episode
import com.example.portalgun.remote.rickandmorty.RickAndMortyService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface IRickAndMortyRepository {

    suspend fun characters(): List<Character>
    fun getPagedCharacterStream(): Flow<PagingData<Character>>

    suspend fun episodes(ids: List<Int>): List<Episode>
}

class RickAndMortyRepository @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) : IRickAndMortyRepository {

    override suspend fun characters() = rickAndMortyService.characters().characters

    override fun getPagedCharacterStream(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RickAndMortyCharacterPagingSource(rickAndMortyService) }
        ).flow
    }

    override suspend fun episodes(ids: List<Int>): List<Episode> {
        return if (ids.size == 1) {
            listOf(episode(ids.first()))
        } else {
            rickAndMortyService.episodes(ids.joinToString(separator = ","))
        }
    }

    private suspend fun episode(id: Int): Episode {
        return rickAndMortyService.episode(id)
    }
}
