package com.example.portalgun.remote.rickandmorty

data class Info(
    val count: Int,
    val pages: Int,
    val next: String,
    val previous: String?
)
