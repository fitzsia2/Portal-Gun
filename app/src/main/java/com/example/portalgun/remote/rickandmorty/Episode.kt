package com.example.portalgun.remote.rickandmorty

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

typealias CharacterUrl = String

@Parcelize
data class Episode(
    val id: Int,
    val name: String,
    @field:Json(name = "air_date") val airDate: String,
    val episode: String,
    @field:Json(name = "characters") val characterUrls: List<CharacterUrl>,
    val url: String,
    val created: String
) : Parcelable
