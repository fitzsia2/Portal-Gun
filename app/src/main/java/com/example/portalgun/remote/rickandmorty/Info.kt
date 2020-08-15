package com.example.portalgun.remote.rickandmorty

import com.squareup.moshi.Json

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    @field:Json(name = "prev") val previous: String?
)
