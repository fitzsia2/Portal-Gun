package com.example.portalgun.remote.rickandmorty

import retrofit2.http.GET
import retrofit2.http.Path
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

    /**
     * Retrieve a list of episodes given a comma separated list of ids
     */
    @GET("episode/{ids}")
    suspend fun episodes(
        @Path("ids") ids: String
    ): List<Episode>

    /**
     * Retrieve a list of episodes given a comma separated list of ids
     */
    @GET("episode/{id}")
    suspend fun episode(
        @Path("id") id: Int
    ): Episode
}
