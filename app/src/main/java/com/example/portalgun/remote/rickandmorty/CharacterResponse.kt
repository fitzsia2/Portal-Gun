package com.example.portalgun.remote.rickandmorty

import com.squareup.moshi.Json

data class CharacterResponse(
    val info: Info,
    @field:Json(name = "results") val characters: List<Character>
)
