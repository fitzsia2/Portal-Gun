package com.example.portalgun.remote.rickandmorty

import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {

    @GET("character")
    suspend fun characters(
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): CharacterResponse
}
